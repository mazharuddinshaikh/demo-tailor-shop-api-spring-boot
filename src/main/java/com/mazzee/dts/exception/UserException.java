package com.mazzee.dts.exception;

import com.mazzee.dts.dto.ApiError;
import com.mazzee.dts.dto.UserDto;

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
	private UserDto userDto;

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

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

}
