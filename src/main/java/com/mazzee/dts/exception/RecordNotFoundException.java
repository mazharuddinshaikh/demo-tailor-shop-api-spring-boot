package com.mazzee.dts.exception;

import com.mazzee.dts.dto.ApiError;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class RecordNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ApiError apiError;

	public RecordNotFoundException(ApiError apiError) {
		super();
		this.apiError = apiError;
	}

	public ApiError getApiError() {
		return apiError;
	}

}
