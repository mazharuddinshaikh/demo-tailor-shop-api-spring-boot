package com.mazzee.dts.dto;

import java.util.List;

public class UserHelpDto {
	private String title;
	private String description;
	private List<UserHelpStepDto> helpStepsList;

	public UserHelpDto() {
		super();
	}

	public UserHelpDto(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<UserHelpStepDto> getHelpStepsList() {
		return helpStepsList;
	}

	public void setHelpStepsList(List<UserHelpStepDto> helpStepsList) {
		this.helpStepsList = helpStepsList;
	}

}
