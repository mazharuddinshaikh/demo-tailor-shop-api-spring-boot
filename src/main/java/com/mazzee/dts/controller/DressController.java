package com.mazzee.dts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mazzee.dts.dto.Dress;
import com.mazzee.dts.service.DressService;
import com.mazzee.dts.utils.ApiError;
import com.mazzee.dts.utils.DtsUtils;
import com.mazzee.dts.utils.RecordNotFoundException;

@RestController
@RequestMapping("api/v1/dress")
public class DressController {
	private final static Logger LOGGER = LoggerFactory.getLogger(DressController.class);
	private DressService dressService;

	@Autowired
	public void setDressService(DressService dressService) {
		this.dressService = dressService;
	}

	@GetMapping("/allDress")
	public ResponseEntity<List<Dress>> getDressList() throws RecordNotFoundException {
		LOGGER.info("Get all dresses");
		ResponseEntity<List<Dress>> responseEntity = null;
		List<Dress> dressList = dressService.getDressList(null);
		if (!DtsUtils.isNullOrEmpty(dressList)) {
			LOGGER.info("Dress list found count {}", dressList.size());
			responseEntity = ResponseEntity.ok().body(dressList);
		} else {
			ApiError apiError = new ApiError(HttpStatus.NO_CONTENT.value(), "Dress list not found ");
			throw new RecordNotFoundException(apiError);
		}
		return responseEntity;
	}

	@GetMapping("/allDress/{dressType}")
	public ResponseEntity<List<Dress>> getDressByDressType(@PathVariable("dressType") String dressType)
			throws RecordNotFoundException {
		LOGGER.info("Get all dresses of dress type {}", dressType);
		ResponseEntity<List<Dress>> responseEntity = null;
		List<Dress> dressList = dressService.getDressList(dressType);
		if (!DtsUtils.isNullOrEmpty(dressList)) {
			LOGGER.info("Dress list found for dress type {} count {}", dressType, dressList.size());
			responseEntity = ResponseEntity.ok().body(dressList);
		} else {
			ApiError apiError = new ApiError(HttpStatus.NO_CONTENT.value(),
					"Dress not found for dress type  " + dressType);
			throw new RecordNotFoundException(apiError);
		}
		return responseEntity;
	}
}
