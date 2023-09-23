package com.mazzee.dts.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.entity.Measurement;
import com.mazzee.dts.repo.MeasurementRepo;
import com.mazzee.dts.utils.DtsUtils;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Service
public class MeasurementService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementService.class);
	private MeasurementRepo measurementRepo;

	@Autowired
	public void setMeasurementRepo(MeasurementRepo measurementRepo) {
		this.measurementRepo = measurementRepo;
	}

	public Optional<Measurement> getMeasureMentByDressId(Integer dressId) {
		LOGGER.info("Get measurement for dress id {}", dressId);
		Optional<Measurement> measurement = Optional.empty();
		if (Objects.nonNull(dressId) && dressId != 0) {
			measurement = measurementRepo.getMeasurementByDressId(dressId);
		}
		return measurement;
	}

	public List<Measurement> getMeasureMentByDressId(List<Integer> dressIdList) {
		List<Measurement> measurementList = null;
		if (!DtsUtils.isNullOrEmpty(dressIdList)) {
			measurementList = measurementRepo.getMeasurementByDressId(dressIdList);
		}
		return measurementList;
	}

	public Measurement updateMeasurement(Measurement measurement) {
		Measurement updatedMeasurement = null;
		if (Objects.nonNull(measurement)) {
			updatedMeasurement = measurementRepo.save(measurement);
		}
		return updatedMeasurement;

	}

	public Measurement getUpdatedEntity(final Measurement measurement) {
		if (Objects.nonNull(measurement)) {
//			measurement image
			String measurementImge = measurement.getMeasurementImage();
			measurementImge = getImageUrl(measurement, measurementImge);
			measurement.setMeasurementImage(measurementImge);
//			seaved image
			String seavedImage = measurement.getSeavedImage();
			seavedImage = getImageUrl(measurement, seavedImage);
			measurement.setSeavedImage(seavedImage);
//			pattern image
			String patternImage = measurement.getPatternImage();
			patternImage = getImageUrl(measurement, patternImage);
			measurement.setPatternImage(patternImage);
//			raw image
			String rawImage = measurement.getRawDressImage();
			rawImage = getImageUrl(measurement, rawImage);
			measurement.setRawDressImage(rawImage);
		}
		return measurement;
	}

	private String getImageUrl(final Measurement measurement, String measurementImge) {
		Pattern pattern = Pattern.compile("\\|\\|");
		Stream<String> result = null;
		final String baseUrl = "";
		if (!DtsUtils.isNullOrEmpty(measurementImge)) {
			result = pattern.splitAsStream(measurementImge);
		}
		if (Objects.nonNull(result)) {
			measurementImge = result
					.map(image -> baseUrl + "/user/U" + measurement.getDress().getCustomer().getUser().getUserId()
							+ "/customer/C" + measurement.getDress().getCustomer().getCustomerId() + "/dress/D"
							+ measurement.getDress().getDressId() + "/" + image)
					.collect(Collectors.joining("||"));
		}
		return measurementImge;
	}

}
