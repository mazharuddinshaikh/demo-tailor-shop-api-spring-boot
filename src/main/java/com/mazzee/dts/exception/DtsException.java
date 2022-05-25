/**
 * 
 */
package com.mazzee.dts.exception;

import com.mazzee.dts.dto.ApiError;

/**
 * @author Admin
 *
 */
public class DtsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ApiError apiError;

	public DtsException(ApiError apiError) {
		super();
		this.apiError = apiError;
	}

	public ApiError getApiError() {
		return apiError;
	}

}
