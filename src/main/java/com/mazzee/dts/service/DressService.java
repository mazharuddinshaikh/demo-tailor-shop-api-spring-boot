package com.mazzee.dts.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
	private static final Logger LOGGER = LoggerFactory.getLogger(DressService.class);
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
			dressList = pageDress.get().toList();
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
				}).toList();
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
			dressDtoList = dressList.stream().map(dress -> dressDtoMapper.getDressDto(dress)).toList();
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
		CustomerDto customerDto = dressDetailDto.getCustomer();
		InvoiceDto invoiceDto = dressDetailDto.getCustomerInvoice();
		Optional<User> userOptional = userService.getUserByUserId(userId);

//		update customer
		if (Objects.nonNull(customerDto) && userOptional.isPresent()) {
			customer = customerService.updateCustomer(customerDto, userOptional.get());
		}
		if (Objects.nonNull(customer)) {
			LOGGER.info("Customer  details updated successfully");
			isDressDetailUpdated = true;
		}
//		update dressDetails
		List<DressDto> dressDtoList = dressDetailDto.getDressList();
		if (!DtsUtils.isNullOrEmpty(dressDtoList)) {
			for (DressDto dressDto : dressDtoList) {
				Dress dress = getUpdatedDress(dressDto, customer);
				dress = dressRepo.save(dress);
				if (Objects.isNull(dress)) {
					isDressDetailUpdated = false;
				} else {
					billAmount += getBillAmount(dress);
					discountedAmount += dress.getDiscountedPrice();
				}
			}
		}
//		update invoice
		if (Objects.nonNull(invoiceDto) && isDressDetailUpdated) {
			invoice = updateInvoice(invoiceDto, customer, billAmount, discountedAmount);
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

	private double getBillAmount(Dress dress) {
		return dress.getNumberOfDress() > 0 ? dress.getNumberOfDress() * dress.getUserDressType().getPrice()
				: dress.getUserDressType().getPrice();
	}

	private Invoice updateInvoice(InvoiceDto invoiceDto, Customer customer, double billAmount,
			double discountedAmount) {
		if (invoiceDto.getBillAmount() <= 0) {
			invoiceDto.setBillAmount(billAmount);
		}
		if (invoiceDto.getDiscountedAmount() <= 0) {
			invoiceDto.setDiscountedAmount(discountedAmount);
		}
		return invoiceService.updateInvoice(invoiceDto, customer);
	}

	private Dress getUpdatedDress(DressDto dressDto, Customer customer) {
		Dress dress = getDressInstanceById(dressDto.getDressId());
		Measurement measurement = getMeasurementInstance(dress);
		dress.setMeasurement(measurement);
		if (Objects.nonNull(dressDto.getDressType())) {
			Optional<UserDressType> userDressType = userDressTypeService
					.getUserDressTypeByUserDressTypeId(dressDto.getDressType().getUserDressTypeId());
			if (userDressType.isPresent()) {
				dress.setUserDressType(userDressType.get());
			}
		}
		dress.setOrderDate(getOrderDate(dressDto.getOrderDate(), customer));
		dress.setDeliveryDate(getDeliveryDate(dressDto.getDeliveryDate(), customer));
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
		return dress;

	}

	private LocalDateTime getDeliveryDate(String deliveryDate, Customer customer) {
		if (!DtsUtils.isNullOrEmpty(deliveryDate)) {
			return DtsUtils.convertStringToDate(deliveryDate, DtsUtils.DATE_FORMAT_1);
		} else {
			if (Objects.nonNull(customer) && Objects.nonNull(customer.getDeliveryDate())) {
				return customer.getDeliveryDate();
			}
		}
		return null;
	}

	private LocalDateTime getOrderDate(String orderDate, Customer customer) {
		if (!DtsUtils.isNullOrEmpty(orderDate)) {
			return DtsUtils.convertStringToDate(orderDate, DtsUtils.DATE_FORMAT_1);
		} else {
			if (Objects.nonNull(customer) && Objects.nonNull(customer.getOrderDate())) {
				return customer.getOrderDate();
			}
		}
		return null;
	}

	private Dress getDressInstanceById(int dressId) {
		Optional<Dress> optionalDress = getDressByDressId(dressId);
		Dress dress = null;
		if (optionalDress.isPresent()) {
			LOGGER.info("Updating dress {}", optionalDress.get().getDressId());
			dress = optionalDress.get();
		} else {
			LOGGER.info("Adding new dress");
			dress = new Dress();
			dress.setCreatedAt(LocalDateTime.now());
		}
		return dress;
	}

	private Measurement getMeasurementInstance(Dress dress) {
		Measurement measurement = dress.getMeasurement();
		if (Objects.nonNull(measurement)) {
			measurement.setUpdatedAt(LocalDateTime.now());
		} else {
			measurement = new Measurement();
			measurement.setDress(dress);
			measurement.setCreatedAt(LocalDateTime.now());
			measurement.setUpdatedAt(LocalDateTime.now());
		}
		return null;
	}

	public Optional<Dress> getDressByDressId(int dressId) {
		LOGGER.info("Get dress by dress id");
		return dressRepo.getDressById(dressId);
	}

	/**
	 * @param userId
	 * @param customerId
	 * @param files      - file names must come in
	 *                   D_dressid_measurementtype_incrementnumber.extension
	 * @return
	 */
	@Transactional(value = TxType.REQUIRED, rollbackOn = { SQLException.class })
	public boolean updateDressImage(int userId, int customerId, List<MultipartFile> files) {
		LOGGER.info("Update dress images");
		boolean isDressImagesUpdated = false;
		Set<Integer> dressIdList = getDressIdList(files);
		LOGGER.info(" dress ids {}", dressIdList);
		if (!DtsUtils.isNullOrEmpty(dressIdList)) {
			for (int dressId : dressIdList) {
				LOGGER.info("Updating dress images of dress is {}", dressId);
				Optional<Dress> optionalDress = getDressByDressId(dressId);
				if (optionalDress.isPresent()) {
					Dress dress = optionalDress.get();
					List<MultipartFile> dressIdFiles = DtsUtils.getMeasurementMultipartFiles(files,
							DtsConstant.DRESS + DtsConstant.UNDERSCORE + dressId);
					if (!DtsUtils.isNullOrEmpty(dressIdFiles)) {
						Measurement measurement = updateMeasurement(dress);
						dress.setMeasurement(measurement);
						isDressImagesUpdated = updateMeasurementImage(dress, files, userId);
					}
				} else {
					LOGGER.info("Dress not found for dress id {}", dressId);
				}
			}
		}
		return isDressImagesUpdated;
	}

	private Measurement updateMeasurement(Dress dress) {
		Measurement measurement = dress.getMeasurement();
		if (Objects.isNull(measurement)) {
			measurement = new Measurement();
			measurement.setDress(dress);
		}
		measurement.setUpdatedAt(LocalDateTime.now());
		measurement = measurementService.updateMeasurement(measurement);
		return measurement;
	}

	private boolean updateMeasurementImage(Dress dress, List<MultipartFile> files, int userId) {
		boolean isDressImagesUpdated = false;
		LOGGER.info("Image found for dress id {}", dress.getDressId());
		measurementImageService.uploadMeasurementImage(files, userId, dress);
		if (Objects.nonNull(dress.getMeasurement())) {
			isDressImagesUpdated = true;
			LOGGER.info("measurement updated successfully for measurement id {} and dress id {}",
					dress.getMeasurement().getMeasurementId(), dress.getDressId());
		}
		return isDressImagesUpdated;
	}

	private Set<Integer> getDressIdList(List<MultipartFile> files) {
		Set<Integer> dressIdList = new HashSet<>();
		if (!DtsUtils.isNullOrEmpty(files)) {
//			file names must come in D_dressid_measurementtype_incrementnumber.extension
			for (MultipartFile multipartFile : files) {
				int dressId = 0;
				String fileName = multipartFile.getOriginalFilename();
				if (!DtsUtils.isNullOrEmpty(fileName)) {
					String[] splitNames = fileName.split("_");
					if (!DtsUtils.isNullOrEmpty(splitNames) && splitNames.length > 1) {
						dressId = Integer.parseInt(splitNames[1]);
						dressIdList.add(dressId);
					}
				}
			}
		}
		return dressIdList;
	}

}
