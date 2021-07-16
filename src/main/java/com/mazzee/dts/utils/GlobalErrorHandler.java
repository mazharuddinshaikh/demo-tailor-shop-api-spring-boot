package com.mazzee.dts.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {

	@ExceptionHandler(value = RecordNotFoundException.class)
	public ResponseEntity<ApiError> recordNotFoundException(RecordNotFoundException exception) {
		ApiError error = exception.getApiError();
		return ResponseEntity.status(error.getHttpStatus()).body(error);
	}

}
