package com.mazzee.dts.service;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.Measurement;
import com.mazzee.dts.repo.MeasurementRepo;
import com.mazzee.dts.utils.DtsUtils;

@Service
public class MeasurementService {
	private final static Logger LOGGER = LoggerFactory.getLogger(MeasurementService.class);
	private MeasurementRepo measurementRepo;

	@Autowired
	public void setMeasurementRepo(MeasurementRepo measurementRepo) {
		this.measurementRepo = measurementRepo;
	}

	public List<Measurement> getMeasureMents(Integer dressId) {
		LOGGER.info("Get measurement for dress id {}", dressId);
		List<Measurement> measurementList = null;
		if (Objects.nonNull(dressId) && dressId != 0) {
			measurementList = measurementRepo.getMeasurementByDressId(dressId);
		} else {
			measurementList = measurementRepo.findAll();
		}
		if (!DtsUtils.isNullOrEmpty(measurementList)) {
			LOGGER.info("Found measurement for dress id {} count {}", dressId, measurementList.size());
		} else {
			LOGGER.info("No measurement Found for dress id {} ", dressId);
		}

		return measurementList;
	}

}
