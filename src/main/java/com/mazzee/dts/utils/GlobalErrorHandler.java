package com.mazzee.dts.utils;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mazzee.dts.dto.ApiError;
import com.mazzee.dts.dto.ApiResponse;
import com.mazzee.dts.dto.UserDto;
import com.mazzee.dts.exception.DtsException;
import com.mazzee.dts.exception.NewRecordNotFoundException;
import com.mazzee.dts.exception.RecordNotFoundException;
import com.mazzee.dts.exception.UserException;

/**
 * Used to handle all errors
 * 
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@ControllerAdvice
public class GlobalErrorHandler {
	private final static Logger LOGGER = LoggerFactory.getLogger(GlobalErrorHandler.class);

	@ExceptionHandler(value = RecordNotFoundException.class)
	public ResponseEntity<ApiError> recordNotFoundException(RecordNotFoundException exception) {
		LOGGER.error(exception.getApiError().getMessage());
		ApiError error = exception.getApiError();
		return ResponseEntity.status(error.getHttpStatus()).body(error);
	}

	@ExceptionHandler(value = NewRecordNotFoundException.class)
	public ResponseEntity<ApiResponse<ApiError>> newRecordNotFoundException(NewRecordNotFoundException exception) {
		LOGGER.error(exception.getApiError().getMessage());
		ApiError error = exception.getApiError();
		ApiResponse<ApiError> apiResponse = new ApiResponse<>();
		apiResponse.setHttpStatus(error.getHttpStatus());
		apiResponse.setMessage(error.getMessage());
		apiResponse.setResult(error);
		return ResponseEntity.status(error.getHttpStatus()).body(apiResponse);
	}

	@ExceptionHandler(value = UserException.class)
	public ResponseEntity<ApiError> userException(UserException exception) {
		UserDto user = exception.getUserDto();
		String userId = null;
		if (Objects.nonNull(user)) {
			if (!DtsUtils.isNullOrEmpty(user.getUserName())) {
				userId = user.getUserName();
			}
			if (!DtsUtils.isNullOrEmpty(user.getEmail()) && DtsUtils.isNullOrEmpty(userId)) {
				userId = user.getEmail();
			}
			if (!DtsUtils.isNullOrEmpty(user.getMobileNo()) && DtsUtils.isNullOrEmpty(userId)) {
				userId = user.getMobileNo();
			}
		}
		LOGGER.error("Login failed for - {} message - {}", userId, exception.getApiError().getMessage());
		ApiError error = exception.getApiError();
		return ResponseEntity.status(error.getHttpStatus()).body(error);
	}

	@ExceptionHandler(value = DtsException.class)
	public ResponseEntity<ApiError> getDtsException(DtsException dtsException) {
		LOGGER.info(dtsException.getApiError().getMessage());
		ApiError error = dtsException.getApiError();
		return ResponseEntity.status(error.getHttpStatus()).body(error);
	}
}
