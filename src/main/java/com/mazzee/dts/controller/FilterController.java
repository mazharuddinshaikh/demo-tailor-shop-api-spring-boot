/**
 * 
 */
package com.mazzee.dts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mazzee.dts.dto.ApiError;
import com.mazzee.dts.dto.DressFilterDto;
import com.mazzee.dts.exception.RecordNotFoundException;
import com.mazzee.dts.service.DressTypeService;
import com.mazzee.dts.utils.DtsUtils;

/**
 * @author Admin
 *
 */
@RestController
@RequestMapping("api/dressFilter")
public class FilterController {
	private final static Logger LOGGER = LoggerFactory.getLogger(FilterController.class);
	private DressTypeService dressTypeService;

	@Autowired
	public void setDressTypeService(DressTypeService dressTypeService) {
		this.dressTypeService = dressTypeService;
	}

	@GetMapping(value = "/v1/allDressFilter")
	public ResponseEntity<List<DressFilterDto>> getAllFilter() throws RecordNotFoundException {
		ResponseEntity<List<DressFilterDto>> responseEntity = null;
		final List<DressFilterDto> dressTypeList = dressTypeService.getAllFilterList();
		if (!DtsUtils.isNullOrEmpty(dressTypeList)) {
			LOGGER.info("Get all dress filters {}", dressTypeList.size());
			responseEntity = ResponseEntity.ok().body(dressTypeList);
		} else {
			ApiError apiError = new ApiError(HttpStatus.NO_CONTENT.value(), "No filter found");
			throw new RecordNotFoundException(apiError);
		}
		return responseEntity;
	}
}
