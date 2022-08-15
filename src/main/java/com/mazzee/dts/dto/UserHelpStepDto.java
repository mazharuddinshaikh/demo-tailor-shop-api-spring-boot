package com.mazzee.dts.dto;

import java.util.List;

public class UserHelpStepDto {
	private String title;
	private String description;
	private List<String> stepsImageList;

	public UserHelpStepDto() {
	}

	public UserHelpStepDto(String title, String description) {
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

	public List<String> getStepsImageList() {
		return stepsImageList;
	}

	public void setStepsImageList(List<String> stepsImageList) {
		this.stepsImageList = stepsImageList;
	}

}
