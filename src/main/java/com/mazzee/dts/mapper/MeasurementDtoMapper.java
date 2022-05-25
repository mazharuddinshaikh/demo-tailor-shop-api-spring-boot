package com.mazzee.dts.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.MeasurementDto;
import com.mazzee.dts.entity.Measurement;
import com.mazzee.dts.utils.DtsUtils;

@Service
public class MeasurementDtoMapper {
	private final DtsModelMapper dtsModelMapper;

	@Autowired
	public MeasurementDtoMapper(DtsModelMapper dtsModelMapper) {
		this.dtsModelMapper = dtsModelMapper;
	}

	private ModelMapper getMeasurementToDtoMapper() {
		Pattern pattern = Pattern.compile("\\|\\|");
		Converter<String, List<String>> imageToListConverter = context -> {
			String measurementImage = context.getSource();
			List<String> imageList = null;
			if (!DtsUtils.isNullOrEmpty(measurementImage)) {
				Stream<String> result = pattern.splitAsStream(measurementImage);
				imageList = result.collect(Collectors.toList());
			}
			return imageList;
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
