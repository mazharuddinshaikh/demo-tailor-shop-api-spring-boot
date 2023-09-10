package com.mazzee.dts.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mazzee.dts.dto.CustomerDto;
import com.mazzee.dts.dto.DressDetailDto;
import com.mazzee.dts.dto.DressDto;
import com.mazzee.dts.dto.InvoiceDto;
import com.mazzee.dts.entity.Customer;
import com.mazzee.dts.entity.Dress;
import com.mazzee.dts.entity.Invoice;
import com.mazzee.dts.entity.Measurement;
import com.mazzee.dts.entity.User;
import com.mazzee.dts.entity.UserDressType;
import com.mazzee.dts.mapper.DressDtoMapper;
import com.mazzee.dts.repo.DressRepo;
import com.mazzee.dts.utils.DtsConstant;
import com.mazzee.dts.utils.DtsUtils;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Service
public class DressService {
	private final static Logger LOGGER = LoggerFactory.getLogger(DressService.class);
	private UserService userService;
	private DressRepo dressRepo;
	private CustomerService customerService;
	private InvoiceService invoiceService;
	private MeasurementService measurementService;
	private UserDressTypeService userDressTypeService;
	private DressDtoMapper dressDtoMapper;
	private MeasurementImageService measurementImageService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setDressRepo(DressRepo dressRepo) {
		this.dressRepo = dressRepo;
	}

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Autowired
	public void setInvoiceService(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@Autowired
	public void setMeasurementService(MeasurementService measurementService) {
		this.measurementService = measurementService;
	}

	@Autowired
	public void setUserDressTypeService(UserDressTypeService userDressTypeService) {
		this.userDressTypeService = userDressTypeService;
	}

	@Autowired
	public void setDressDtoMapper(DressDtoMapper dressDtoMapper) {
		this.dressDtoMapper = dressDtoMapper;
	}

	@Autowired
	public void setMeasurementImageService(MeasurementImageService measurementImageService) {
		this.measurementImageService = measurementImageService;
	}

	public List<Dress> getDressList(String dressType) {
		List<Dress> dressList = null;
		if (DtsUtils.isNullOrEmpty(dressType)) {
			LOGGER.info("Get dress list");
			dressList = dressRepo.getAllDress();
		} else {
			LOGGER.info("Get dress list for dress type {}", dressType);
			dressList = dressRepo.getAllDressByDressType(dressType);
		}
		return dressList;
	}

	public List<DressDto> getDressListByUser(List<String> dressType, int userId, int offset, int limit) {
		Page<Dress> pageDress = null;
		List<Dress> dressList = null;
		List<DressDto> dressDtoList = null;
		if (DtsUtils.isNullOrEmpty(dressType)) {
			LOGGER.info("Get dress list");
			pageDress = dressRepo.getDressListByUser(userId, PageRequest.of(offset, limit));
		} else {
			LOGGER.info("Get dress list for dress type {}", dressType);
			pageDress = dressRepo.getDressListByUserAndDressType(userId, dressType, PageRequest.of(offset, limit));
		}
		if (Objects.nonNull(pageDress)) {
			dressList = pageDress.get().collect(Collectors.toList());
			dressDtoList = getDressDtoList(dressList);
		}
		return dressDtoList;
	}

	public DressDetailDto getDressDetail(int userId, int customerId) {
		LOGGER.info("get dress details for customer {}", customerId);
		InvoiceDto invoiceDto = null;
		CustomerDto customerDto = null;
		List<DressDto> dressDtoList = null;
		DressDetailDto dressDetails = null;
//		getCustoemr details
		Optional<Customer> customer = customerService.getCustomerById(customerId);
		if (customer.isPresent()) {
			dressDetails = new DressDetailDto();
			customerDto = customerService.getCustomerDto(customer.get());
//		get dress list of customer
			LOGGER.info("get dress list by user {} and customer {}", userId, customerId);
			List<Dress> dressList = dressRepo.getDressListByUserIdAndCustomerId(userId, customerId);
			dressDtoList = getDressDtoList(dressList);
//			remove customer object from dress list
			if (!DtsUtils.isNullOrEmpty(dressDtoList)) {
				LOGGER.info("Removing user object from dto");
				dressDtoList = dressDtoList.stream().map(dress -> {
					dress.setCustomer(null);
					return dress;
				}).collect(Collectors.toList());
			}
//		get invoice of customer
			Optional<Invoice> invoice = invoiceService.getInvoiceByCustomerId(customerId);
			invoiceDto = invoice.isPresent() ? invoiceService.getInvoiceDto(invoice.get()) : null;
			dressDetails.setCustomer(customerDto);
			dressDetails.setDressList(dressDtoList);
			dressDetails.setCustomerInvoice(invoiceDto);
		}
		return dressDetails;
	}

	public DressDto getDressDto(Dress dress) {
		DressDto dressDto = null;
		if (Objects.nonNull(dress)) {
			dressDto = dressDtoMapper.getDressDto(dress);
		}
		return dressDto;
	}

	public List<DressDto> getDressDtoList(List<Dress> dressList) {
		LOGGER.info("Converting entity list to dto");
		List<DressDto> dressDtoList = null;
		if (!DtsUtils.isNullOrEmpty(dressList)) {
			dressDtoList = dressList.stream().map(dress -> dressDtoMapper.getDressDto(dress))
					.collect(Collectors.toList());
		}
		return dressDtoList;
	}

	@Transactional(value = TxType.REQUIRED, rollbackOn = { SQLException.class })
	public int updateDress(final int userId, final DressDetailDto dressDetailDto) {
		LOGGER.info("Updating dress details");
		int customerId = 0;
		double discountedAmount = 0.0;
		double billAmount = 0.0;
		Customer customer = null;
		Invoice invoice = null;
		boolean isDressDetailUpdated = false;
		User user = null;
		CustomerDto customerDto = dressDetailDto.getCustomer();
		InvoiceDto invoiceDto = dressDetailDto.getCustomerInvoice();
		Optional<User> userOptional = userService.getUserByUserId(userId);

		if (userOptional.isPresent()) {
			user = userOptional.get();
		}

//		update customer
		if (Objects.nonNull(customerDto)) {
			customer = customerService.updateCustomer(customerDto, user);
		}
		if (Objects.nonNull(customer)) {
			LOGGER.info("Customer  details updated successfully");
			isDressDetailUpdated = true;
		}
//		update dressDetails
		List<DressDto> dressDtoList = dressDetailDto.getDressList();
		if (!DtsUtils.isNullOrEmpty(dressDtoList)) {
			for (DressDto dressDto : dressDtoList) {
				Optional<Dress> optionalDress = getDressByDressId(dressDto.getDressId());
				Dress dress = null;
				if (optionalDress.isPresent()) {
					LOGGER.info("Updating dress {}", optionalDress.get().getDressId());
					dress = getUpdatedDress(optionalDress.get(), dressDto, customer);
				} else {
					LOGGER.info("Adding new dress");
					dress = getUpdatedDress(null, dressDto, customer);
				}
				dress = dressRepo.save(dress);
				if (Objects.isNull(dress)) {
					isDressDetailUpdated = false;
				} else {
					int numberOfDress = dress.getNumberOfDress();
					if (Objects.nonNull(dress.getUserDressType())) {
						if (numberOfDress > 0) {
							billAmount += numberOfDress * dress.getUserDressType().getPrice();
						} else {
							billAmount += dress.getUserDressType().getPrice();
						}
						discountedAmount += dress.getDiscountedPrice();
					}
				}
			}
		}
//		update invoice
		if (Objects.nonNull(invoiceDto)) {
			if (invoiceDto.getBillAmount() <= 0) {
				invoiceDto.setBillAmount(billAmount);
			}
			if (invoiceDto.getDiscountedAmount() <= 0) {
				invoiceDto.setDiscountedAmount(discountedAmount);
			}
		}

		if (Objects.nonNull(invoiceDto) && isDressDetailUpdated) {
			invoice = invoiceService.updateInvoice(invoiceDto, customer);
		}
		if (Objects.nonNull(invoice)) {
			LOGGER.info("invoice  details updated successfully");
			isDressDetailUpdated = true;
		}
		if (isDressDetailUpdated) {
			customerId = customer.getCustomerId();
		}
		return customerId;
	}

	private Dress getUpdatedDress(Dress dress, DressDto dressDto, Customer customer) {
		Measurement measurement = null;
		if (Objects.isNull(dress)) {
			dress = new Dress();
			dress.setCreatedAt(LocalDateTime.now());
		} else {
			measurement = dress.getMeasurement();
		}
		if (Objects.nonNull(measurement)) {
			measurement.setUpdatedAt(LocalDateTime.now());
		} else {
			measurement = new Measurement();
			measurement.setDress(dress);
			measurement.setCreatedAt(LocalDateTime.now());
			measurement.setUpdatedAt(LocalDateTime.now());
		}
		dress.setMeasurement(measurement);
		if (Objects.nonNull(dressDto)) {
			if (Objects.nonNull(dressDto.getDressType())) {
				Optional<UserDressType> userDressType = userDressTypeService
						.getUserDressTypeByUserDressTypeId(dressDto.getDressType().getUserDressTypeId());
				if (userDressType.isPresent()) {
					dress.setUserDressType(userDressType.get());
				}
			}
			if (!DtsUtils.isNullOrEmpty(dressDto.getOrderDate())) {
				LocalDateTime orderDate = DtsUtils.convertStringToDate(dressDto.getOrderDate(), DtsUtils.DATE_FORMAT_1);
				dress.setOrderDate(orderDate);
			} else {
				if (Objects.nonNull(customer) && Objects.nonNull(customer.getOrderDate())) {
					dress.setOrderDate(customer.getOrderDate());
				}
			}
			if (!DtsUtils.isNullOrEmpty(dressDto.getDeliveryDate())) {
				LocalDateTime deliveryDate = DtsUtils.convertStringToDate(dressDto.getDeliveryDate(),
						DtsUtils.DATE_FORMAT_1);
				dress.setDeliveryDate(deliveryDate);
			} else {
				if (Objects.nonNull(customer) && Objects.nonNull(customer.getDeliveryDate())) {
					dress.setDeliveryDate(customer.getDeliveryDate());
				}
			}
			if (DtsUtils.isNullOrEmpty(dressDto.getDeliveryStatus())) {
				dressDto.setDeliveryStatus("UNDELIVERED");
			}
			dress.setDeliveryStatus(dressDto.getDeliveryStatus());
			if (dressDto.getNumberOfDress() != 0) {
				dress.setNumberOfDress(dressDto.getNumberOfDress());
			} else {
				dress.setNumberOfDress(1);
			}
			dress.setPrice(dressDto.getPrice());
			if (dressDto.getDiscountedPrice() != 0) {
				dress.setDiscountedPrice(dressDto.getDiscountedPrice());
			} else {
				dress.setDiscountedPrice(dressDto.getPrice());
			}
			dress.setPaymentStatus(dressDto.getPaymentStatus());
			dress.setUpdatedAt(LocalDateTime.now());
			dress.setComment(dressDto.getComment());
			dress.setCustomer(customer);
		}
		return dress;

	}

	public Optional<Dress> getDressByDressId(int dressId) {
		LOGGER.info("Get dress by dress id");
		return dressRepo.getDressById(dressId);
	}

	@Transactional(value = TxType.REQUIRED, rollbackOn = { SQLException.class })
	public boolean updateDressImage(int userId, int customerId, List<MultipartFile> files) {
		LOGGER.info("Update dress images");
		boolean isDressImagesUpdated = false;

		Set<Integer> dressIdList = null;
		if (!DtsUtils.isNullOrEmpty(files)) {
//			file names must come in D_dressid_measurementtype_incrementnumber.extension
			for (MultipartFile multipartFile : files) {
				int dressId = 0;
				dressIdList = new HashSet<>();
				String fileName = multipartFile.getOriginalFilename();
				String[] splitNames = fileName.split("_");
				dressId = Integer.parseInt(splitNames[1].substring(0));
				dressIdList.add(dressId);
			}
		}
		LOGGER.info(" dress ids {}", dressIdList);
		if (!DtsUtils.isNullOrEmpty(dressIdList)) {
			for (int dressId : dressIdList) {
				LOGGER.info("Updating dress images of dress is {}", dressId);
				Measurement measurement = null;
				Optional<Dress> optionalDress = getDressByDressId(dressId);
				Dress dress = null;
				if (optionalDress.isPresent()) {
					dress = optionalDress.get();
					measurement = dress.getMeasurement();
					if (Objects.isNull(measurement)) {
						measurement = new Measurement();
						measurement.setDress(optionalDress.get());
					}
					List<MultipartFile> dressIdFiles = DtsUtils.getMeasurementMultipartFiles(files,
							DtsConstant.DRESS + DtsConstant.UNDERSCORE + dressId);
					if (!DtsUtils.isNullOrEmpty(dressIdFiles)) {
						measurement.setUpdatedAt(LocalDateTime.now());
						measurement = measurementService.updateMeasurement(measurement);
						LOGGER.info("Image found for dress id {}", dressId);
						measurementImageService.uploadMeasurementImage(files, measurement, userId, dress);
						if (Objects.nonNull(measurement)) {
							isDressImagesUpdated = true;
							LOGGER.info("measurement updated successfully for measurement id {} and dress id {}",
									measurement.getMeasurementId(), dressId);
						}
					}
				} else {
					LOGGER.info("Dress not found for dress id {}", dressId);
				}
			}
		}
		return isDressImagesUpdated;
	}

}
