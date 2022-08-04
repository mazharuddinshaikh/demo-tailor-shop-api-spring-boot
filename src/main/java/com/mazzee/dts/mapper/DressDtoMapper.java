/**
 * 
 */
package com.mazzee.dts.mapper;

import java.time.LocalDateTime;
import java.util.Objects;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.CustomerDto;
import com.mazzee.dts.dto.DressDto;
import com.mazzee.dts.dto.MeasurementDto;
import com.mazzee.dts.dto.UserDressTypeDto;
import com.mazzee.dts.entity.Customer;
import com.mazzee.dts.entity.Dress;
import com.mazzee.dts.entity.Measurement;
import com.mazzee.dts.service.CustomerService;
import com.mazzee.dts.service.MeasurementService;

/**
 * @author Admin
 *
 */
@Service
public class DressDtoMapper {
	private final DtsModelMapper dtsModelMapper;
	private MeasurementDtoMapper measurementDtoMapper;
//	private DressTypeDtoMapper dressTypeDtoMapper;
	private UserDressTypeDtoMapper userDressTypeDtoMapper;
	private MeasurementService measurementService;
	private CustomerService customerService;
	@Value("${external.image.path}")
	private String imagePath;

	@Autowired
	public DressDtoMapper(DtsModelMapper dtsModelMapper) {
		this.dtsModelMapper = dtsModelMapper;
	}

	@Autowired
	public void setUserDressTypeDtoMapper(UserDressTypeDtoMapper userDressTypeDtoMapper) {
		this.userDressTypeDtoMapper = userDressTypeDtoMapper;
	}

	@Autowired
	public void setMeasurementDtoMapper(MeasurementDtoMapper measurementDtoMapper) {
		this.measurementDtoMapper = measurementDtoMapper;
	}

//	@Autowired
//	public void setDressTypeDtoMapper(DressTypeDtoMapper dressTypeDtoMapper) {
//		this.dressTypeDtoMapper = dressTypeDtoMapper;
//	}

	@Autowired
	public void setMeasurementService(MeasurementService measurementService) {
		this.measurementService = measurementService;
	}

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	private ModelMapper getDressToDtoMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Converter<LocalDateTime, String> dateConverter = dtsModelMapper.getDateConverter();
		Converter<LocalDateTime, String> timeConverter = dtsModelMapper.getTimeConverter();

		modelMapper.typeMap(Dress.class, DressDto.class).addMappings(mapper -> {
			mapper.map(Dress::getDressId, DressDto::setDressId);
			mapper.using(dateConverter).map(s -> s.getOrderDate(), DressDto::setOrderDate);
			mapper.using(timeConverter).map(s -> s.getOrderDate(), DressDto::setOrderTime);
			mapper.map(Dress::getUserDressType, DressDto::setDressType);
			mapper.using(dateConverter).map(s -> s.getDeliveryDate(), DressDto::setDeliveryDate);
			mapper.using(timeConverter).map(s -> s.getDeliveryDate(), DressDto::setDeliveryTime);
			mapper.map(Dress::getDeliveryStatus, DressDto::setDeliveryStatus);
			mapper.map(Dress::getNumberOfDress, DressDto::setNumberOfDress);
			mapper.map(Dress::getPrice, DressDto::setPrice);
			mapper.map(Dress::getDiscountedPrice, DressDto::setDiscountedPrice);
			mapper.map(Dress::getPaymentStatus, DressDto::setPaymentStatus);
			mapper.map(Dress::getComment, DressDto::setComment);
		});

		return modelMapper;
	}

	private ModelMapper getDtoToDressMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.typeMap(DressDto.class, Dress.class).addMappings(mapper -> {
			mapper.map(DressDto::getDressType, Dress::setUserDressType);
			mapper.map(s -> s.getDeliveryDate(), Dress::setDeliveryDate);
			mapper.map(DressDto::getDeliveryStatus, Dress::setDeliveryStatus);
			mapper.map(DressDto::getNumberOfDress, Dress::setNumberOfDress);
			mapper.map(DressDto::getPrice, Dress::setPrice);
			mapper.map(DressDto::getDiscountedPrice, Dress::setDiscountedPrice);
			mapper.map(DressDto::getPaymentStatus, Dress::setPaymentStatus);
			mapper.map(DressDto::getComment, Dress::setComment);
		});

		return modelMapper;

	}

	public DressDto getDressDto(Dress dress) {
		DressDto dressDto = null;
		ModelMapper dressModelMapper = getDressToDtoMapper();
		if (Objects.nonNull(dress)) {
			dressDto = dressModelMapper.map(dress, DressDto.class);
			Measurement measurement = measurementService.getUpdatedEntity(dress.getMeasurement());
			MeasurementDto measurementDto = measurementDtoMapper.getMeasurementDto(measurement);
			UserDressTypeDto userDressTypeDto = userDressTypeDtoMapper.getUserDressTypeDto(dress.getUserDressType());
			Customer customer = dress.getCustomer();
			CustomerDto customerDto = customerService.getCustomerDto(customer);
			dressDto.setCustomer(customerDto);
			dressDto.setMeasurement(measurementDto);
			dressDto.setDressType(userDressTypeDto);
		}
		return dressDto;
	}

	public Dress getDress(DressDto dressDto) {
		Dress dress = null;
		ModelMapper dressModelMapper = getDtoToDressMapper();
		if (Objects.nonNull(dressDto)) {
			dress = dressModelMapper.map(dressDto, Dress.class);
		}
		return dress;
	}

}
