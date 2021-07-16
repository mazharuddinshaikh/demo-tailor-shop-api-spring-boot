package com.mazzee.dts.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.Measurement;
import com.mazzee.dts.repo.MeasurementRepo;

@Service
public class MeasurementService {
	private MeasurementRepo measurementRepo;

	@Autowired
	public void setMeasurementRepo(MeasurementRepo measurementRepo) {
		this.measurementRepo = measurementRepo;
	}

	public List<Measurement> getMeasureMents(Integer dressId) {

		List<Measurement> measurementList = null;
		if (Objects.nonNull(dressId) && dressId != 0) {
			measurementList = measurementRepo.getMeasurementByDressId(dressId);
		} else {
			measurementList = measurementRepo.findAll();
		}
		return measurementList;
	}

}
