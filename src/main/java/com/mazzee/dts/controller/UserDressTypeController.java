package com.mazzee.dts.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mazzee.dts.dto.ApiError;
import com.mazzee.dts.dto.ApiResponse;
import com.mazzee.dts.dto.UserDressTypeDto;
import com.mazzee.dts.entity.UserDressType;
import com.mazzee.dts.exception.DtsException;
import com.mazzee.dts.exception.RecordNotFoundException;
import com.mazzee.dts.service.UserDressTypeService;
import com.mazzee.dts.utils.DtsUtils;

@RestController
@RequestMapping("api/userDressType/")
public class UserDressTypeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDressTypeController.class);
	private UserDressTypeService userDressTypeService;

	@Autowired
	public void setUserDressTypeService(UserDressTypeService userDressTypeService) {
		this.userDressTypeService = userDressTypeService;
	}

	@GetMapping("/v1/userDressType")
	public ResponseEntity<ApiResponse<List<UserDressTypeDto>>> getUserDressTypeByUser(
			@RequestParam(name = "userId", required = true) int userId) throws RecordNotFoundException {
		LOGGER.info("Get all User Dress types");
		ResponseEntity<ApiResponse<List<UserDressTypeDto>>> responseEntity = null;
		List<UserDressTypeDto> userDressTypeList = userDressTypeService.getUserDressTypeListByUserId(userId);
//		new user dress types not available
		if (DtsUtils.isNullOrEmpty(userDressTypeList)) {
			LOGGER.info("New user. Adding default dress types");
			List<UserDressType> userDressTypes = userDressTypeService.addAllDefaultUserDressType(userId);
			if (!DtsUtils.isNullOrEmpty(userDressTypes)) {
				userDressTypeList = userDressTypeService.getUserDressTypeListByUserId(userId);
			}
		}
		if (!DtsUtils.isNullOrEmpty(userDressTypeList)) {
			ApiResponse<List<UserDressTypeDto>> apiResponse = new ApiResponse<>();
			apiResponse.setHttpStatus(HttpStatus.OK.value());
			apiResponse.setMessage("User Dress types list found");
			apiResponse.setResult(userDressTypeList);
			responseEntity = ResponseEntity.ok().body(apiResponse);
		} else {
			ApiError apiError = new ApiError(HttpStatus.NO_CONTENT.value(), "User Dress types not found ");
			throw new RecordNotFoundException(apiError);
		}
		return responseEntity;
	}

	@PostMapping(value = "/v1/updateUserDressType", consumes = {
			MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ApiResponse<UserDressTypeDto>> updateUserDressType(
			@RequestBody UserDressTypeDto userDressTypeDto) throws DtsException {
		LOGGER.info("Update User dress type");
		String message = null;
		ResponseEntity<ApiResponse<UserDressTypeDto>> responseEntity = null;
		ApiError apiError = null;
		UserDressType userDressType = userDressTypeService.updateUserDressType(userDressTypeDto);
		UserDressTypeDto updatedDto = null;
		if (Objects.nonNull(userDressType)) {
			updatedDto = userDressTypeService.getUserDressTypeDto(userDressType);
		}

		if (Objects.isNull(updatedDto)) {
			LOGGER.info("Something went wrong! dress type not updated");
			apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Something went wrong! dress type not updated");
			throw new DtsException(apiError);
		}
		message = "Dress type updated successfully";
		LOGGER.info("Dress type {} updated successfully", updatedDto.getUserDressTypeId());
		ApiResponse<UserDressTypeDto> userResponse = new ApiResponse<>();
		userResponse.setHttpStatus(HttpStatus.OK.value());
		userResponse.setMessage(message);
		userResponse.setResult(updatedDto);
		responseEntity = ResponseEntity.ok().body(userResponse);
		return responseEntity;
	}
}
