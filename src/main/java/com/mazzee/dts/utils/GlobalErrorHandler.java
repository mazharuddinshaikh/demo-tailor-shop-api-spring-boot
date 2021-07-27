package com.mazzee.dts.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {
	private final static Logger LOGGER = LoggerFactory.getLogger(GlobalErrorHandler.class);

	@ExceptionHandler(value = RecordNotFoundException.class)
	public ResponseEntity<ApiError> recordNotFoundException(RecordNotFoundException exception) {
		LOGGER.info("Record not found {}", exception);
		ApiError error = exception.getApiError();
		return ResponseEntity.status(error.getHttpStatus()).body(error);
	}

}
