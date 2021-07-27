/**
 * 
 */
package com.mazzee.dts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mazzee.dts.dto.DressType;
import com.mazzee.dts.service.DressTypeService;

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
	public ResponseEntity<List<DressType>> getDressType() {
		LOGGER.info("Get Dress types");
		List<DressType> dressTypeList = dressTypeService.getAllDressTypes();
		ResponseEntity<List<DressType>> result = ResponseEntity.ok().body(dressTypeList);

		return result;
	}

}
