package com.mazzee.dts.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mazzee.dts.dto.ApiError;
import com.mazzee.dts.dto.ApiResponse;
import com.mazzee.dts.dto.UserHelpDto;
import com.mazzee.dts.exception.RecordNotFoundException;
import com.mazzee.dts.service.UserHelpService;
import com.mazzee.dts.utils.DtsUtils;

@RestController
@RequestMapping("api/userHelp/")
public class UserHelpController {
	private final static Logger LOGGER = LoggerFactory.getLogger(UserHelpController.class);
	private UserHelpService userHelpService;

	@Autowired
	public void setUserHelpService(UserHelpService userHelpService) {
		this.userHelpService = userHelpService;
	}

	@GetMapping("/v1/appGuide")
	public ResponseEntity<ApiResponse<List<UserHelpDto>>> getAppGuideList(HttpServletRequest request)
			throws RecordNotFoundException {
		String hostUrl = getHostUrl(request);
		String contextPath = request.getContextPath();
		String baseUrl = getBaseUrl(contextPath, hostUrl);
		LOGGER.info("Get user help document");
		ResponseEntity<ApiResponse<List<UserHelpDto>>> responseEntity = null;
		List<UserHelpDto> userHelpList = userHelpService.getAppGuideList(baseUrl);
		if (!DtsUtils.isNullOrEmpty(userHelpList)) {
			ApiResponse<List<UserHelpDto>> apiResponse = new ApiResponse<>();
			apiResponse.setHttpStatus(HttpStatus.OK.value());
			apiResponse.setMessage("Help document found");
			apiResponse.setResult(userHelpList);
			responseEntity = ResponseEntity.ok().body(apiResponse);
		} else {
			ApiError apiError = new ApiError(HttpStatus.NO_CONTENT.value(), "Help document not found ");
			throw new RecordNotFoundException(apiError);
		}
		return responseEntity;
	}

	private String getHostUrl(HttpServletRequest request) {
		return ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();

	}

	private String getBaseUrl(String contextPath, String hostUrl) {
		return DtsUtils.isNullOrEmpty(contextPath) ? hostUrl : (hostUrl + contextPath);
	}
}
