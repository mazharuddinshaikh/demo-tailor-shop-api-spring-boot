package com.mazzee.dts.controller;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mazzee.dts.DemoTailorShopApplication;
import com.mazzee.dts.dto.Dress;
import com.mazzee.dts.service.DressService;

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
	public ResponseEntity<List<Dress>> getDressList() {
		LOGGER.info("Get all dresses");
		List<Dress> dressList = dressService.getDressList(null);
		ResponseEntity<List<Dress>> responseEntity = ResponseEntity.ok().body(dressList);
		System.out.println("controller closed");
		return responseEntity;
	}

	@GetMapping("/allDress/{dressType}")
	public ResponseEntity<List<Dress>> getDressByDressType(@PathVariable("dressType") String dressType) {
		LOGGER.info("Get all dresses of dress type {}", dressType);
		List<Dress> dressList = dressService.getDressList(dressType);
		ResponseEntity<List<Dress>> responseEntity = ResponseEntity.ok().body(dressList);
		return responseEntity;
	}
}
