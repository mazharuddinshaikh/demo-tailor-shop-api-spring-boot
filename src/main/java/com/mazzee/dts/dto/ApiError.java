package com.mazzee.dts.dto;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class ApiError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int httpStatus;
	private String message;
	private Collection<String> errorList;

	public ApiError() {
		super();
	}

	public ApiError(int httpStatus, String message) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Collection<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(Collection<String> errorList) {
		this.errorList = errorList;
	}

}
