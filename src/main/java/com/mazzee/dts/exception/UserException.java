package com.mazzee.dts.exception;

import com.mazzee.dts.dto.ApiError;
import com.mazzee.dts.dto.User;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 */
public class UserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ApiError apiError;
	private User user;

	public UserException(ApiError apiError) {
		super();
		this.apiError = apiError;
	}

	public ApiError getApiError() {
		return apiError;
	}

	public void setApiError(ApiError apiError) {
		this.apiError = apiError;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
