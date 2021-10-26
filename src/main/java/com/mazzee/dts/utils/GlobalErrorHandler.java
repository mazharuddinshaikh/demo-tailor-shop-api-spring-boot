package com.mazzee.dts.utils;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mazzee.dts.dto.User;

@ControllerAdvice
public class GlobalErrorHandler {
	private final static Logger LOGGER = LoggerFactory.getLogger(GlobalErrorHandler.class);

	@ExceptionHandler(value = RecordNotFoundException.class)
	public ResponseEntity<ApiError> recordNotFoundException(RecordNotFoundException exception) {
		LOGGER.error(exception.getApiError().getMessage());
		ApiError error = exception.getApiError();
		return ResponseEntity.status(error.getHttpStatus()).body(error);
	}

	@ExceptionHandler(value = UserException.class)
	public ResponseEntity<ApiError> userException(UserException exception) {
		User user = exception.getUser();
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

}
