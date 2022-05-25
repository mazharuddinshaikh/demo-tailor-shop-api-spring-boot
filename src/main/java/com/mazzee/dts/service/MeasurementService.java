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

import com.mazzee.dts.aws.AwsS3Util;
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
	private final static Logger LOGGER = LoggerFactory.getLogger(MeasurementService.class);
	private MeasurementRepo measurementRepo;
	private AwsS3Util awsS3Util;

	@Autowired
	public void setMeasurementRepo(MeasurementRepo measurementRepo) {
		this.measurementRepo = measurementRepo;
	}

	@Autowired
	public void setAwsS3Util(AwsS3Util awsS3Util) {
		this.awsS3Util = awsS3Util;
	}

	public Optional<Measurement> getMeasureMentByDressId(Integer dressId) {
		LOGGER.info("Get measurement for dress id {}", dressId);
		Optional<Measurement> measurement = Optional.empty();
		if (Objects.nonNull(dressId) && dressId != 0) {
			measurement = measurementRepo.getMeasurementByDressId(dressId);
		}
		if (measurement.isPresent()) {
			LOGGER.info("Found measurement for dress id {} measurement id {}", dressId,
					measurement.get().getMeasurementId());
		} else {
			LOGGER.info("Measurement not found for dress id {} ", dressId);
		}
		return measurement;
	}

	public List<Measurement> getMeasureMentByDressId(List<Integer> dressIdList) {
//		LOGGER.info("Get measurement for dress id {}", dressId);
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
		final String baseUrl = awsS3Util.getAwsS3ImageBaseUrl();
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
