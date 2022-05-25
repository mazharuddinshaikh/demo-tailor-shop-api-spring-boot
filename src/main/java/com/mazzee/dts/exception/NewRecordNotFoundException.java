package com.mazzee.dts.exception;

import com.mazzee.dts.dto.ApiError;

public class NewRecordNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ApiError apiError;

	public NewRecordNotFoundException(ApiError apiError) {
		super();
		this.apiError = apiError;
	}

	public ApiError getApiError() {
		return apiError;
	}
}
