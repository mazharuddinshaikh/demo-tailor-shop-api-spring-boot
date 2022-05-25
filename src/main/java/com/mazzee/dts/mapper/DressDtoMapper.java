/**
 * 
 */
package com.mazzee.dts.mapper;

import java.time.LocalDateTime;
import java.util.Objects;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.DressDto;
import com.mazzee.dts.dto.DressTypeDto;
import com.mazzee.dts.dto.MeasurementDto;
import com.mazzee.dts.entity.Dress;
import com.mazzee.dts.entity.DressType;
import com.mazzee.dts.entity.Measurement;
import com.mazzee.dts.service.MeasurementService;

/**
 * @author Admin
 *
 */
@Service
public class DressDtoMapper {
	private final DtsModelMapper dtsModelMapper;
	private MeasurementDtoMapper measurementDtoMapper;
	private DressTypeDtoMapper dressTypeDtoMapper;
	private MeasurementService measurementService;
	@Value("${external.image.path}")
	private String imagePath;

	@Autowired
	public DressDtoMapper(DtsModelMapper dtsModelMapper) {
		this.dtsModelMapper = dtsModelMapper;
	}

	@Autowired
	public void setMeasurementDtoMapper(MeasurementDtoMapper measurementDtoMapper) {
		this.measurementDtoMapper = measurementDtoMapper;
	}

	@Autowired
	public void setDressTypeDtoMapper(DressTypeDtoMapper dressTypeDtoMapper) {
		this.dressTypeDtoMapper = dressTypeDtoMapper;
	}

	@Autowired
	public void setMeasurementService(MeasurementService measurementService) {
		this.measurementService = measurementService;
	}

	private ModelMapper getDressToDtoMapper() {
		ModelMapper modelMapper = new ModelMapper();
//		DateTimeFormatter defaultFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

		Converter<LocalDateTime, String> dateConverter = dtsModelMapper.getDateConverter();
		Converter<LocalDateTime, String> timeConverter = dtsModelMapper.getTimeConverter();

//		Converter<List<Measurement>, String> measurementImageConverter = context -> {
//			List<Measurement> measurementList = context.getSource();
//			String measurementImage = null;
//			if (!DtsUtils.isNullOrEmpty(measurementList)) {
//				Measurement measurement = measurementList.get(0);
//				if (Objects.nonNull(measurement)) {
//					String image = measurement.getMeasurementImage();
//					if (!DtsUtils.isNullOrEmpty(image)) {
//						measurementImage = imagePath + image;
//					}
//				}
//			}
//			return measurementImage;
//		};
//		Converter<List<Measurement>, String> patternImageConverter = context -> {
//			List<Measurement> measurementList = context.getSource();
//			String patternImage = null;
//			if (!DtsUtils.isNullOrEmpty(measurementList)) {
//				Measurement measurement = measurementList.get(0);
//				if (Objects.nonNull(measurement)) {
//					String image = measurement.getPatternImage();
//					if (!DtsUtils.isNullOrEmpty(image)) {
//						patternImage = imagePath + image;
//					}
//				}
//			}
//			return patternImage;
//		};
//		Converter<List<Measurement>, String> seavedImageConverter = context -> {
//			List<Measurement> measurementList = context.getSource();
//			String seavedImage = null;
//			if (!DtsUtils.isNullOrEmpty(measurementList)) {
//				Measurement measurement = measurementList.get(0);
//				if (Objects.nonNull(measurement)) {
//					String image = measurement.getSeavedImage();
//					if (!DtsUtils.isNullOrEmpty(image)) {
//						seavedImage = imagePath + image;
//					}
//				}
//			}
//			return seavedImage;
//		};
//		Converter<List<Measurement>, String> rawImageConverter = context -> {
//			List<Measurement> measurementList = context.getSource();
//			String rawImage = null;
//			if (!DtsUtils.isNullOrEmpty(measurementList)) {
//				Measurement measurement = measurementList.get(0);
//				if (Objects.nonNull(measurement)) {
//					String image = measurement.getRawDressImage();
//					if (!DtsUtils.isNullOrEmpty(image)) {
//						rawImage = imagePath + image;
//					}
//				}
//			}
//			return rawImage;
//		};
		Converter<DressType, Integer> dressTypeIdConverter = context -> {
			if (Objects.nonNull(context.getSource())) {
				return Objects.nonNull(context.getSource()) ? context.getSource().getTypeId() : 0;
			}
			return 0;
		};

		modelMapper.typeMap(Dress.class, DressDto.class).addMappings(mapper -> {
			mapper.map(Dress::getDressId, DressDto::setDressId);
//			mapper.using(dressTypeIdConverter).map(s -> s.getDressType(), DressDto::setDressTypeId);
			mapper.using(dateConverter).map(s -> s.getOrderDate(), DressDto::setOrderDate);
			mapper.using(timeConverter).map(s -> s.getOrderDate(), DressDto::setOrderTime);
			mapper.map(Dress::getDressType, DressDto::setDressType);
			mapper.using(dateConverter).map(s -> s.getDeliveryDate(), DressDto::setDeliveryDate);
			mapper.using(timeConverter).map(s -> s.getDeliveryDate(), DressDto::setDeliveryTime);
			mapper.map(Dress::getDeliveryStatus, DressDto::setDeliveryStatus);
			mapper.map(Dress::getNumberOfDress, DressDto::setNumberOfDress);
			mapper.map(Dress::getPrice, DressDto::setPrice);
			mapper.map(Dress::getDiscountedPrice, DressDto::setDiscountedPrice);
			mapper.map(Dress::getPaymentStatus, DressDto::setPaymentStatus);
			mapper.map(Dress::getComment, DressDto::setComment);
			mapper.map(Dress::getMeasurement, DressDto::setMeasurement);
			mapper.map(Dress::getCustomer, DressDto::setCustomer);
//			mapper.using(measurementImageConverter).map(s -> s.getMeasurementList(), DressDto::setMeasurementImage);
//			mapper.using(patternImageConverter).map(s -> s.getMeasurementList(), DressDto::setPatternImage);
//			mapper.using(seavedImageConverter).map(s -> s.getMeasurementList(), DressDto::setSeavedImage);
//			mapper.using(rawImageConverter).map(s -> s.getMeasurementList(), DressDto::setRawImage);
		});

		return modelMapper;
	}

	private ModelMapper getDtoToDressMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.typeMap(DressDto.class, Dress.class).addMappings(mapper -> {
//			mapper.map(DressDto::getDressId, DressDto::setDressId);
//			mapper.using(dressTypeIdConverter).map(s -> s.getDressType(), DressDto::setDressTypeId);
//			mapper.using(dateConverter).map(s -> s.getOrderDate(), DressDto::setOrderDate);
//			mapper.using(timeConverter).map(s -> s.getOrderDate(), DressDto::setOrderTime);
			mapper.map(DressDto::getDressType, Dress::setDressType);
			mapper.map(s -> s.getDeliveryDate(), Dress::setDeliveryDate);
			mapper.map(DressDto::getDeliveryStatus, Dress::setDeliveryStatus);
			mapper.map(DressDto::getNumberOfDress, Dress::setNumberOfDress);
			mapper.map(DressDto::getPrice, Dress::setPrice);
			mapper.map(DressDto::getDiscountedPrice, Dress::setDiscountedPrice);
			mapper.map(DressDto::getPaymentStatus, Dress::setPaymentStatus);
			mapper.map(DressDto::getComment, Dress::setComment);
//			mapper.map(DressDto::getMeasurement, Dress::setMeasurement);
//			mapper.map(DressDto::getCustomer, Dress::setCustomer);
//			mapper.using(measurementImageConverter).map(s -> s.getMeasurementList(), DressDto::setMeasurementImage);
//			mapper.using(patternImageConverter).map(s -> s.getMeasurementList(), DressDto::setPatternImage);
//			mapper.using(seavedImageConverter).map(s -> s.getMeasurementList(), DressDto::setSeavedImage);
//			mapper.using(rawImageConverter).map(s -> s.getMeasurementList(), DressDto::setRawImage);
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
			DressTypeDto dressTypeDto = dressTypeDtoMapper.getDressTypeDto(dress.getDressType());
			dressDto.setMeasurement(measurementDto);
			dressDto.setDressType(dressTypeDto);
		}
		return dressDto;
	}

	public Dress getDress(DressDto dressDto) {
		Dress dress = null;
		ModelMapper dressModelMapper = getDtoToDressMapper();
		if (Objects.nonNull(dressDto)) {
			dress = dressModelMapper.map(dressDto, Dress.class);
//			MeasurementDto measurementDto = measurementDtoMapper.getMeasurementDto(dress.getMeasurement());
//			DressTypeDto dressTypeDto = dressTypeDtoMapper.getDressTypeDto(dress.getDressType());
//			dressDto.setMeasurement(measurementDto);
//			dressDto.setDressType(dressTypeDto);
		}
		return dress;
	}

}
