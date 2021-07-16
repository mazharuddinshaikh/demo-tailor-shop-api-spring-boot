/**
 * 
 */
package com.mazzee.dts.controller;

import java.util.List;

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

	private DressTypeService dressTypeService;

	@Autowired
	public void setDressTypeService(DressTypeService dressTypeService) {
		this.dressTypeService = dressTypeService;
	}

	@GetMapping(value = "/allDressTypes")
	public ResponseEntity<List<DressType>> getDressType() {
		List<DressType> dressTypeList = dressTypeService.getAllDressTypes();
		ResponseEntity<List<DressType>> result = ResponseEntity.ok().body(dressTypeList);

		return result;
	}

}
