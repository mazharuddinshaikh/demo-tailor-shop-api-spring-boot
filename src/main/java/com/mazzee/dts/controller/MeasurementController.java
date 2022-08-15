package com.mazzee.dts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mazzee.dts.service.MeasurementService;

/**
 * Class define all API related to measurements
 * 
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("api/measurement/")
public class MeasurementController {
	private final static Logger LOGGER = LoggerFactory.getLogger(MeasurementController.class);
	private MeasurementService measurementService;

	@Autowired
	public void setMeasurementService(MeasurementService measurementService) {
		this.measurementService = measurementService;
	}

//	@GetMapping("v1/measurement")
//	public ResponseEntity<List<Measurement>> getMeasurementByDressId(
//			@RequestParam(value = "dressId", required = false) Integer dressId) throws RecordNotFoundException {
//		LOGGER.info("Get measurement for dress id {}", dressId);
//		List<Measurement> measurementList = measurementService.getMeasureMents(dressId);
//		ResponseEntity<List<Measurement>> responseEntity = null;
//		if (!DtsUtils.isNullOrEmpty(measurementList)) {
//			LOGGER.info("Found measurement for dress id {} count {}", dressId, measurementList.size());
//			responseEntity = ResponseEntity.ok().body(measurementList);
//		} else {
//			ApiError apiError = new ApiError(HttpStatus.NO_CONTENT.value(),
//					"Measurement not found for dress id " + dressId);
//			throw new RecordNotFoundException(apiError);
//		}
//		return responseEntity;
//	}

}
