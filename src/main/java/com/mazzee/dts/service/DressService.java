package com.mazzee.dts.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mazzee.dts.aws.AwsS3Util;
import com.mazzee.dts.dto.CustomerDto;
import com.mazzee.dts.dto.DressDetailDto;
import com.mazzee.dts.dto.DressDto;
import com.mazzee.dts.dto.InvoiceDto;
import com.mazzee.dts.entity.Customer;
import com.mazzee.dts.entity.Dress;
import com.mazzee.dts.entity.Invoice;
import com.mazzee.dts.entity.Measurement;
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
	private DressRepo dressRepo;
	private CustomerService customerService;
	private InvoiceService invoiceService;
	private MeasurementService measurementService;
	private DressDtoMapper dressDtoMapper;
	private AwsS3Util awsS3Util;
	@Value("${dts.amazon.s3.bucketname.rootfolder}")
	private String rootFolder;

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
	public void setDressDtoMapper(DressDtoMapper dressDtoMapper) {
		this.dressDtoMapper = dressDtoMapper;
	}

	@Autowired
	public void setAwsS3Util(AwsS3Util awsS3Util) {
		this.awsS3Util = awsS3Util;
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
			List<Dress> dressList = dressRepo.getDressListByUserIdAndCustomerId(userId, customerId);
			dressDtoList = getDressDtoList(dressList);
//			remove customer object from dress list
			if (!DtsUtils.isNullOrEmpty(dressDtoList)) {
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
		List<DressDto> dressDtoList = null;
		if (!DtsUtils.isNullOrEmpty(dressList)) {
			dressDtoList = dressList.stream().map(dress -> dressDtoMapper.getDressDto(dress))
					.collect(Collectors.toList());
		}
		return dressDtoList;
	}

	public boolean updateDress(final int userId, final DressDetailDto dressDetailDto) {
		Customer customer = null;
		Invoice invoice = null;
		boolean isDressDetailUpdated = false;
//		update customer
		CustomerDto customerDto = dressDetailDto.getCustomer();
		if (Objects.nonNull(customerDto)) {
			customer = customerService.updateCustomer(customerDto);
		}
		if (Objects.nonNull(customer)) {
			isDressDetailUpdated = true;
		}
//		update invoice
		InvoiceDto invoiceDto = dressDetailDto.getCustomerInvoice();
		if (Objects.nonNull(invoiceDto) && isDressDetailUpdated) {
			invoice = invoiceService.updateInvoice(invoiceDto, customerDto.getCustomerId());
		}
		if (Objects.nonNull(invoice)) {
			isDressDetailUpdated = true;
		}
//		update dressDetails
		List<DressDto> dressDtoList = dressDetailDto.getDressList();
		if (!DtsUtils.isNullOrEmpty(dressDtoList)) {
			for (DressDto dressDto : dressDtoList) {
				Optional<Dress> optionalDress = getDressByDressId(dressDto.getDressId());
				if (optionalDress.isPresent()) {
					Dress dress = optionalDress.get();
					if (Objects.nonNull(dress)) {
						Measurement measurement = dress.getMeasurement();
						measurement.setUpdatedAt(LocalDateTime.now());
						measurement = measurementService.updateMeasurement(measurement);
					}
					DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
					LocalDate parsedOrderDate = LocalDate.parse(dressDto.getOrderDate(), dateTimeFormatter);
					LocalDateTime orderDate = LocalDateTime.of(parsedOrderDate, LocalTime.now());
					dress.setOrderDate(orderDate);
					LocalDate parsedDeliveryDate = LocalDate.parse(dressDto.getDeliveryDate(), dateTimeFormatter);
					LocalDateTime deliveryDate = LocalDateTime.of(parsedDeliveryDate, LocalTime.now());
					dress.setDeliveryDate(deliveryDate);
					dress.setDeliveryStatus(dressDto.getDeliveryStatus());
					dress.setNumberOfDress(dressDto.getNumberOfDress());
					dress.setPrice(dressDto.getPrice());
					dress.setDiscountedPrice(dressDto.getDiscountedPrice());
					dress.setPaymentStatus(dressDto.getPaymentStatus());
					dress.setUpdatedAt(LocalDateTime.now());
					dress.setComment(dressDto.getComment());
					dress = dressRepo.save(dress);
					if (Objects.isNull(dress)) {
						isDressDetailUpdated = false;
					}

				}
			}
		}

		return isDressDetailUpdated;
	}

	public Optional<Dress> getDressByDressId(int dressId) {
		return dressRepo.getDressById(dressId);
	}

	public void updateDressImage(int userId, List<Integer> dressIdList, List<MultipartFile> files) {
		LOGGER.info("Update dress images");
		Map<String, File> fileMap = new HashMap<>();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
		final String currentDate = dateFormat.format(LocalDate.now());
		for (int dressId : dressIdList) {
			LOGGER.info("Updating dress images of dress is {}", dressId);
			Measurement measurement = null;
			Optional<Dress> optionalDress = getDressByDressId(dressId);
			Dress dress = null;
//			Optional<Measurement> measurementOptional = measurementService.getMeasureMentByDressId(dressId);
			if (optionalDress.isPresent()) {
				dress = optionalDress.get();
				measurement = dress.getMeasurement();
				if (Objects.isNull(measurement)) {
					measurement = new Measurement();
					measurement.setDress(optionalDress.get());
				}
				List<MultipartFile> dressIdFiles = getMeasurementMultipartFiles(files,
						DtsConstant.DRESS + DtsConstant.UNDERSCORE + dressId);
				if (!DtsUtils.isNullOrEmpty(dressIdFiles)) {
					LOGGER.info("Image found for dress id {}", dressId);
					String rawImage = measurement.getRawDressImage();
					String patternImage = measurement.getPatternImage();
					String seavedImage = measurement.getSeavedImage();
					String measurementImage = measurement.getMeasurementImage();
					List<MultipartFile> rawImageList = getMeasurementMultipartFiles(files, DtsConstant.DRESS
							+ DtsConstant.UNDERSCORE + dressId + DtsConstant.UNDERSCORE + DtsConstant.RAW);
					List<MultipartFile> patternImageList = getMeasurementMultipartFiles(files, DtsConstant.DRESS
							+ DtsConstant.UNDERSCORE + dressId + DtsConstant.UNDERSCORE + DtsConstant.PATTERN);
					List<MultipartFile> seavedImageList = getMeasurementMultipartFiles(files, DtsConstant.DRESS
							+ DtsConstant.UNDERSCORE + dressId + DtsConstant.UNDERSCORE + DtsConstant.SEAVED);
					List<MultipartFile> measurementImageList = getMeasurementMultipartFiles(files, DtsConstant.DRESS
							+ DtsConstant.UNDERSCORE + dressId + DtsConstant.UNDERSCORE + DtsConstant.MEASUREMENT);

//				raw image
					if (!DtsUtils.isNullOrEmpty(rawImageList)) {
						for (MultipartFile multipartFile : rawImageList) {
							String fileExtension = getFileExtension(multipartFile);
							String fileName = getFileNameFormat(userId, currentDate, dress, DtsConstant.RAW);
							int i = getIncrementCounter(rawImage, fileName);
							fileName += i + "." + fileExtension;
							rawImage = getUpdatedValue(rawImage, fileName);
							File uploadFile = getUploadFile(multipartFile, fileName);
							String s3Key = getFileNameKey(userId, dress, fileName);
							fileMap.put(s3Key, uploadFile);
//							uploadFileList.add(uploadFile);
						}
					}
//paattern
					if (!DtsUtils.isNullOrEmpty(patternImageList)) {
						for (MultipartFile multipartFile : patternImageList) {
							String fileExtension = getFileExtension(multipartFile);
							String fileName = getFileNameFormat(userId, currentDate, dress, DtsConstant.PATTERN);
							int i = getIncrementCounter(patternImage, fileName);
							fileName += i + "." + fileExtension;
							patternImage = getUpdatedValue(patternImage, fileName);
							File uploadFile = getUploadFile(multipartFile, fileName);
							String s3Key = getFileNameKey(userId, dress, fileName);
							fileMap.put(s3Key, uploadFile);
//							uploadFileList.add(uploadFile);
						}
					}
//				seaved
					if (!DtsUtils.isNullOrEmpty(seavedImageList)) {
						for (MultipartFile multipartFile : seavedImageList) {
							String fileExtension = getFileExtension(multipartFile);
							String fileName = getFileNameFormat(userId, currentDate, dress, DtsConstant.SEAVED);
							int i = getIncrementCounter(seavedImage, fileName);
							fileName += i + "." + fileExtension;
							seavedImage = getUpdatedValue(seavedImage, fileName);
							File uploadFile = getUploadFile(multipartFile, fileName);
							String s3Key = getFileNameKey(userId, dress, fileName);
							fileMap.put(s3Key, uploadFile);
//							uploadFileList.add(uploadFile);
						}
					}
//				measurement
					if (!DtsUtils.isNullOrEmpty(measurementImageList)) {
						for (MultipartFile multipartFile : measurementImageList) {
							String fileExtension = getFileExtension(multipartFile);
							String fileName = getFileNameFormat(userId, currentDate, dress, DtsConstant.MEASUREMENT);
							int i = getIncrementCounter(measurementImage, fileName);
							fileName += i + "." + fileExtension;
							measurementImage = getUpdatedValue(measurementImage, fileName);
							File uploadFile = getUploadFile(multipartFile, fileName);
							String s3Key = getFileNameKey(userId, dress, fileName);
							fileMap.put(s3Key, uploadFile);
//							uploadFileList.add(uploadFile);
						}
					}
					LOGGER.info("Update images measurement for measurement id {}", measurement.getMeasurementId());
					measurement.setRawDressImage(rawImage);
					measurement.setPatternImage(patternImage);
					measurement.setSeavedImage(seavedImage);
					measurement.setMeasurementImage(measurementImage);
					measurement.setUpdatedAt(LocalDateTime.now());
					measurement = measurementService.updateMeasurement(measurement);
					if (Objects.nonNull(measurement)) {
						LOGGER.info("measurement updated successfully for measurement id {} and dress id {}",
								measurement.getMeasurementId(), dressId);
					}
				}
			} else {
				LOGGER.info("Dress not found for dress id {}", dressId);
			}
		}
		if (!DtsUtils.isNullOrEmpty(fileMap)) {
//			upload file to s3 storage
			LOGGER.info("Uploading images to S3 storage");
			int uploadCount = awsS3Util.uploadFile(fileMap);
			LOGGER.info("Images uploading count {}", uploadCount);
//			delete files that are created 
			fileMap.values().stream().forEach(file -> {
				try {
					Files.deleteIfExists(file.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}

	}

	private String getUpdatedValue(String imageName, String fileName) {
		if (!DtsUtils.isNullOrEmpty(imageName)) {
			imageName += "||" + fileName;
		} else {
			imageName = fileName;
		}
		return imageName;
	}

	private String getFileNameKey(int userId, Dress dress, String fileName) {
		String s3Key = rootFolder + "/user/" + DtsConstant.USER + userId + "/customer/" + DtsConstant.CUSTOMER
				+ dress.getCustomer().getCustomerId() + "/dress/" + DtsConstant.DRESS + dress.getDressId() + "/"
				+ fileName;
		return s3Key;
	}

	private File getUploadFile(MultipartFile multipartFile, String fileName) {
		File file = null;
		Path path = Paths.get(fileName);
		try {
			multipartFile.transferTo(path);
			file = path.toFile();
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	private List<MultipartFile> getMeasurementMultipartFiles(List<MultipartFile> files, String startName) {
		return files.stream().filter(file -> file.getOriginalFilename().startsWith(startName))
				.collect(Collectors.toList());
	}

	private String getFileExtension(MultipartFile multipartFile) {
		return multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
	}

	private int getIncrementCounter(String rawImage, String fileName) {
		int incrementCounter = 1;
		boolean isIncreasing = false;
		if (!DtsUtils.isNullOrEmpty(rawImage)) {
			if (rawImage.contains(fileName)) {
				isIncreasing = true;
				while (isIncreasing) {
					incrementCounter++;
					if (!rawImage.contains(fileName + incrementCounter)) {
						isIncreasing = false;
					}
				}
			}
		}
		return incrementCounter;
	}

	private String getFileNameFormat(int userId, final String currentDate, Dress dress, String measuremetnType) {
		String fileName = DtsConstant.USER + userId + DtsConstant.UNDERSCORE + DtsConstant.CUSTOMER
				+ dress.getCustomer().getCustomerId() + DtsConstant.UNDERSCORE + DtsConstant.DRESS + dress.getDressId()
				+ DtsConstant.UNDERSCORE + DtsConstant.FILTER_BY_DRESS_TYPE + dress.getDressType().getTypeId()
				+ DtsConstant.UNDERSCORE + measuremetnType + DtsConstant.UNDERSCORE + currentDate
				+ DtsConstant.UNDERSCORE;
		return fileName;
	}

}
