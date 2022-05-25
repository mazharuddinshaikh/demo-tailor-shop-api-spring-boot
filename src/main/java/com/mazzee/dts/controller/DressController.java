package com.mazzee.dts.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
@RequestMapping("api/dress")
public class DressController {
	private final static Logger LOGGER = LoggerFactory.getLogger(DressController.class);
	private DressService dressService;

	@Autowired
	public void setDressService(DressService dressService) {
		this.dressService = dressService;
	}

	@GetMapping("/v1/allDress/{userId}/{offset}/{limit}")
	public ResponseEntity<ApiResponse<List<DressDto>>> getDressListByUser(
			@RequestParam(name = "dressType", required = false) List<String> dressTypes,
			@PathVariable(name = "userId") Integer userId, @PathVariable(name = "offset") Integer offset,
			@PathVariable(name = "limit") Integer limit) throws RecordNotFoundException, IOException {
		String externalImagePath = new ClassPathResource("/").getFile().getAbsolutePath();
		LOGGER.info("External image path {}", externalImagePath);
		LOGGER.info("External image path {}",
				ServletUriComponentsBuilder.fromCurrentContextPath().path("/").path("dts-images").toUriString());
		LOGGER.info("Get all dresses");
		ResponseEntity<ApiResponse<List<DressDto>>> responseEntity = null;
		List<DressDto> dressList = dressService.getDressListByUser(dressTypes, userId, offset, limit);
		if (!DtsUtils.isNullOrEmpty(dressList)) {
			LOGGER.info("Dress list found count {}", dressList.size());
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

	@GetMapping("/v1/allDress/dressDetail/{userId}/{customerId}")
	public ResponseEntity<ApiResponse<DressDetailDto>> getDressDetail(@PathVariable("userId") int userId,
			@PathVariable("customerId") int customerId) throws NewRecordNotFoundException {
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

	@PostMapping(value = "/v1/allDress/updateDress/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ApiResponse<String>> updateDressDetail(@PathVariable("userId") int userId,
			@RequestBody DressDetailDto dressDetail) throws DtsException {
		String message = "Dress detail updated successfully";
		ResponseEntity<ApiResponse<String>> responseEntity = null;
		boolean isDressDetailUpdated = false;
		ApiError apiError = null;
		isDressDetailUpdated = dressService.updateDress(userId, dressDetail);
		if (isDressDetailUpdated) {
			ApiResponse<String> userResponse = new ApiResponse<>();
			userResponse.setHttpStatus(HttpStatus.OK.value());
			userResponse.setMessage(message);
			responseEntity = ResponseEntity.ok().body(userResponse);
		} else {
			apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Something went wrong! Dress detail not updated");
			throw new DtsException(apiError);
		}
		return responseEntity;
	}

	@PostMapping(value = "/v1/allDress/updateDress/updateImages/{userId}", consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE

	})
	public void updateDressImages(@PathVariable("userId") Integer userId,
			@RequestParam("dressId") List<Integer> dressIdList, @RequestParam("files") List<MultipartFile> files) {
		LOGGER.info("Update dress images");
		if (!DtsUtils.isNullOrEmpty(files) && !DtsUtils.isNullOrEmpty(dressIdList)) {
			dressService.updateDressImage(userId, dressIdList, files);

		}

	}
}
