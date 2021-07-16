package com.mazzee.dts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mazzee.dts.dto.Measurement;
import com.mazzee.dts.service.MeasurementService;

@RestController
@RequestMapping("api/v1/measurements")
public class MeasurementController {
	private MeasurementService measurementService;

	@Autowired
	public void setMeasurementService(MeasurementService measurementService) {
		this.measurementService = measurementService;
	}

	@GetMapping("/measurement")
	public ResponseEntity<List<Measurement>> getMeasurementByDressId(
			@RequestParam(value = "dressId", required = false) Integer dressId) {
		List<Measurement> measurementList = measurementService.getMeasureMents(dressId);
		ResponseEntity<List<Measurement>> responseEntity = ResponseEntity.ok().body(measurementList);
		return responseEntity;
	}

}