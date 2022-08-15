package com.mazzee.dts.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mazzee.dts.dto.ApiError;
import com.mazzee.dts.dto.ApiResponse;
import com.mazzee.dts.dto.DressDetailDto;
import com.mazzee.dts.dto.DressDto;
import com.mazzee.dts.exception.DtsException;
import com.mazzee.dts.exception.NewRecordNotFoundException;
import com.mazzee.dts.exception.RecordNotFoundException;
import com.mazzee.dts.service.DressService;
import com.mazzee.dts.utils.DtsUtils;

// Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXpoYXIiLCJpYXQiOjE2NDY3NDE0NTUsImlzcyI6ImR0cy10YWlsb3Itc2hvcCJ9.DONBg4UJwUyt9aQLVGZliLu5XqmzEgAO9a7Bixvk8q0
/**
 * Class define all API related to dress
 * 
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("api/dress/")
public class DressController {
	private final static Logger LOGGER = LoggerFactory.getLogger(DressController.class);
	private DressService dressService;

	@Autowired
	public void setDressService(DressService dressService) {
		this.dressService = dressService;
	}

	@GetMapping("v1/allDress/{userId}/{offset}/{limit}")
	public ResponseEntity<ApiResponse<List<DressDto>>> getDressListByUser(
			@RequestParam(name = "dressType", required = false) List<String> dressTypes,
			@PathVariable(name = "userId") Integer userId, @PathVariable(name = "offset") Integer offset,
			@PathVariable(name = "limit") Integer limit) throws RecordNotFoundException, IOException {
		LOGGER.info("Get dress list");
		ResponseEntity<ApiResponse<List<DressDto>>> responseEntity = null;
		List<DressDto> dressList = dressService.getDressListByUser(dressTypes, userId, offset, limit);
		if (!DtsUtils.isNullOrEmpty(dressList)) {
			ApiResponse<List<DressDto>> apiResponse = new ApiResponse<>();
			apiResponse.setHttpStatus(HttpStatus.OK.value());
			apiResponse.setMessage("Dress list found");
			apiResponse.setResult(dressList);
			responseEntity = ResponseEntity.ok().body(apiResponse);
		} else {
			ApiError apiError = new ApiError(HttpStatus.NO_CONTENT.value(), "Dress list not found ");
			throw new RecordNotFoundException(apiError);
		}
		return responseEntity;
	}

	@GetMapping("v1/dressDetail/{userId}/{customerId}")
	public ResponseEntity<ApiResponse<DressDetailDto>> getDressDetail(@PathVariable("userId") int userId,
			@PathVariable("customerId") int customerId) throws NewRecordNotFoundException {
		LOGGER.info("Get dress detail");
		ResponseEntity<ApiResponse<DressDetailDto>> responseEntity = null;
		ApiResponse<DressDetailDto> dressResponse = new ApiResponse<>();
		DressDetailDto dressDetails = dressService.getDressDetail(userId, customerId);
		if (Objects.nonNull(dressDetails)) {
			dressResponse.setHttpStatus(HttpStatus.OK.value());
			dressResponse.setMessage("Dress Details found");
			dressResponse.setResult(dressDetails);
		} else {
			ApiError apiError = new ApiError(HttpStatus.NO_CONTENT.value(), "Dress Details not found");
			throw new NewRecordNotFoundException(apiError);
		}
		responseEntity = ResponseEntity.ok().body(dressResponse);
		return responseEntity;
	}

	@PostMapping(value = "v1/updateDress/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ApiResponse<DressDetailDto>> updateDressDetail(@PathVariable("userId") int userId,
			@RequestBody DressDetailDto dressDetail) throws DtsException {
		LOGGER.info("Updating dress details");
		String message = "Dress detail updated successfully";
		ResponseEntity<ApiResponse<DressDetailDto>> responseEntity = null;

		ApiError apiError = null;
		DressDetailDto updatedDressDetails = null;
		int updatedCustomerId = dressService.updateDress(userId, dressDetail);
		if (updatedCustomerId > 0) {
			updatedDressDetails = dressService.getDressDetail(userId, updatedCustomerId);
		}
		if (Objects.nonNull(updatedDressDetails)) {
			ApiResponse<DressDetailDto> dressResponse = new ApiResponse<>();
			dressResponse.setHttpStatus(HttpStatus.OK.value());
			dressResponse.setMessage(message);
			dressResponse.setResult(updatedDressDetails);
			responseEntity = ResponseEntity.ok().body(dressResponse);
		} else {
			apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Something went wrong! Dress detail not updated");
			throw new DtsException(apiError);
		}
		return responseEntity;
	}

	@PostMapping(value = "v1/updateDress/updateImages/{userId}/{customerId}")
	public ResponseEntity<ApiResponse<DressDetailDto>> updateDressImages(@PathVariable("userId") Integer userId,
			@PathVariable("customerId") Integer customerId, @RequestParam("files") List<MultipartFile> files)
			throws DtsException {
		LOGGER.info("Update dress images");
		DressDetailDto dressDetailDto = null;
		ApiError apiError = null;
		boolean isDressImagesUpdated = dressService.updateDressImage(userId, customerId, files);
		if (isDressImagesUpdated) {
			dressDetailDto = dressService.getDressDetail(userId, customerId);
		}
		ResponseEntity<ApiResponse<DressDetailDto>> responseEntity = null;
		if (Objects.nonNull(dressDetailDto)) {
			ApiResponse<DressDetailDto> dressResponse = new ApiResponse<>();
			dressResponse.setHttpStatus(HttpStatus.OK.value());
			dressResponse.setMessage("added dress image");
			dressResponse.setResult(dressDetailDto);
			responseEntity = ResponseEntity.ok().body(dressResponse);
		} else {
			apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Something went wrong! Dress images not updated");
			throw new DtsException(apiError);
		}
		return responseEntity;
	}
}
