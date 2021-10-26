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

import com.mazzee.dts.dto.DressType;
import com.mazzee.dts.service.DressTypeService;
import com.mazzee.dts.utils.ApiError;
import com.mazzee.dts.utils.DtsUtils;
import com.mazzee.dts.utils.RecordNotFoundException;

/**
 * @author mazhar
 *
 */
@RestController
@RequestMapping("api/v1/dressTypes")
public class DressTypeController {
	private final static Logger LOGGER = LoggerFactory.getLogger(DressTypeController.class);
	private DressTypeService dressTypeService;

	@Autowired
	public void setDressTypeService(DressTypeService dressTypeService) {
		this.dressTypeService = dressTypeService;
	}

	@GetMapping(value = "/allDressTypes")
	public ResponseEntity<List<DressType>> getDressType() throws RecordNotFoundException {
		LOGGER.info("Get all dress types");
		ResponseEntity<List<DressType>> responseEntity = null;
		final List<DressType> dressTypeList = dressTypeService.getAllDressTypes();
		if (!DtsUtils.isNullOrEmpty(dressTypeList)) {
			LOGGER.info("Get all dress types count {}", dressTypeList.size());
			responseEntity = ResponseEntity.ok().body(dressTypeList);
		} else {
			ApiError apiError = new ApiError(HttpStatus.NO_CONTENT.value(), "Dress type not found ");
			throw new RecordNotFoundException(apiError);
		}
		return responseEntity;
	}

}
