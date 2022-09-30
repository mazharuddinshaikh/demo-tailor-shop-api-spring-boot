package com.mazzee.dts.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mazzee.dts.dto.AndroidAppDto;
import com.mazzee.dts.dto.ApiError;
import com.mazzee.dts.dto.ApiResponse;
import com.mazzee.dts.exception.RecordNotFoundException;
import com.mazzee.dts.service.AndroidAppService;

@RestController
@RequestMapping("api/app/")
public class AndroidAppController {

	private AndroidAppService androidAppService;

	@Autowired
	public void setAndroidAppService(AndroidAppService androidAppService) {
		this.androidAppService = androidAppService;
	}

	@GetMapping("/v1/appUpdate")
	public ResponseEntity<ApiResponse<AndroidAppDto>> getAppGuideList() throws RecordNotFoundException {
		ResponseEntity<ApiResponse<AndroidAppDto>> responseEntity = null;

		AndroidAppDto androidAppDto = androidAppService.getLatestUpdate();

		if (Objects.nonNull(androidAppDto)) {
			ApiResponse<AndroidAppDto> apiResponse = new ApiResponse<>();
			apiResponse.setHttpStatus(HttpStatus.OK.value());
			apiResponse.setMessage("Update data found");
			apiResponse.setResult(androidAppDto);
			responseEntity = ResponseEntity.ok().body(apiResponse);
		} else {
			ApiError apiError = new ApiError(HttpStatus.NO_CONTENT.value(), "App update not found");
			throw new RecordNotFoundException(apiError);
		}

		return responseEntity;
	}
}
