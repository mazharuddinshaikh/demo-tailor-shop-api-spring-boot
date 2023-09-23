package com.mazzee.dts.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.DtsImageProperties;
import com.mazzee.dts.dto.MeasurementDto;
import com.mazzee.dts.dto.MeasurementImageDto;
import com.mazzee.dts.entity.Measurement;
import com.mazzee.dts.entity.MeasurementImage;
import com.mazzee.dts.utils.DtsConstant;
import com.mazzee.dts.utils.DtsUtils;

@Service
public class MeasurementDtoMapper {
	private final DtsModelMapper dtsModelMapper;
	private DtsImageProperties dtsImageProperties;

	@Autowired
	public MeasurementDtoMapper(DtsModelMapper dtsModelMapper) {
		this.dtsModelMapper = dtsModelMapper;
	}

	@Autowired
	public void setMeasurementImageUtil(DtsImageProperties dtsImageProperties) {
		this.dtsImageProperties = dtsImageProperties;
	}

	private ModelMapper getMeasurementToDtoMapper() {
		Pattern pattern = Pattern.compile("\\|\\|");
		Converter<String, List<String>> imageToListConverter = context -> {
			String measurementImage = context.getSource();
			List<String> imageList = null;
			if (!DtsUtils.isNullOrEmpty(measurementImage)) {
				Stream<String> result = pattern.splitAsStream(measurementImage);
				imageList = result.toList();
			}
			return imageList;
		};

		Converter<List<MeasurementImage>, MeasurementImageDto> measurementImageDtoConverter = context -> {
			MeasurementImageDto measurementImageDto = new MeasurementImageDto();
			List<MeasurementImage> measurementList = context.getSource();
			List<String> rawImageList = measurementList.stream()
					.filter(image -> image.getImageType().equals(DtsConstant.RAW))
					.map(image -> dtsImageProperties.getBasePath() + "/" + dtsImageProperties.getBaseFolder() + "/"
							+ image.getImagePath() + "/" + image.getImageName())
					.toList();
			List<String> patternImageList = measurementList.stream()
					.filter(image -> image.getImageType().equals(DtsConstant.PATTERN))
					.map(image -> dtsImageProperties.getBasePath() + "/" + dtsImageProperties.getBaseFolder() + "/"
							+ image.getImagePath() + "/" + image.getImageName())
					.toList();
			List<String> seavedImageList = measurementList.stream()
					.filter(image -> image.getImageType().equals(DtsConstant.SEAVED))
					.map(image -> dtsImageProperties.getBasePath() + "/" + dtsImageProperties.getBaseFolder() + "/"
							+ image.getImagePath() + "/" + image.getImageName())
					.toList();
			List<String> measurementImageList = measurementList.stream()
					.filter(image -> image.getImageType().equals(DtsConstant.MEASUREMENT))
					.map(image -> dtsImageProperties.getBasePath() + "/" + dtsImageProperties.getBaseFolder() + "/"
							+ image.getImagePath() + "/" + image.getImageName())
					.toList();
			measurementImageDto.setMeasurementImageList(measurementImageList);
			measurementImageDto.setPatternImageList(patternImageList);
			measurementImageDto.setSeavedImageList(seavedImageList);
			measurementImageDto.setRawImageList(rawImageList);
			return measurementImageDto;
		};
		ModelMapper modelMapper = new ModelMapper();
		Converter<LocalDateTime, String> dateTimeConverter = dtsModelMapper.getDateTimeConverter();
		modelMapper.typeMap(Measurement.class, MeasurementDto.class).addMappings(mapper -> {
			mapper.map(Measurement::getMeasurementId, MeasurementDto::setMeasurementId);
			mapper.map(Measurement::getComment, MeasurementDto::setComment);
			mapper.using(dateTimeConverter).map(Measurement::getCreatedAt, MeasurementDto::setCreatedAt);
			mapper.using(dateTimeConverter).map(Measurement::getUpdatedAt, MeasurementDto::setUpdatedAt);
			mapper.map(Measurement::getComment, MeasurementDto::setComment);
			mapper.using(imageToListConverter).map(Measurement::getMeasurementImage,
					MeasurementDto::setMeasurementImageList);
			mapper.using(imageToListConverter).map(Measurement::getPatternImage, MeasurementDto::setPatternImageList);
			mapper.using(imageToListConverter).map(Measurement::getRawDressImage, MeasurementDto::setRawDressImageList);
			mapper.using(imageToListConverter).map(Measurement::getSeavedImage, MeasurementDto::setSeavedImageList);
			mapper.using(measurementImageDtoConverter).map(Measurement::getMeasurementImageList,
					MeasurementDto::setMeasurementImage);
		});
		return modelMapper;
	}

	public MeasurementDto getMeasurementDto(Measurement measurement) {
		ModelMapper modelMapper = getMeasurementToDtoMapper();
		MeasurementDto measurementDto = null;
		if (Objects.nonNull(measurement)) {
			measurementDto = modelMapper.map(measurement, MeasurementDto.class);
		}
		return measurementDto;
	}

}
