package com.mazzee.dts.dto;

import java.util.List;

public class AndroidAppDto {
	private String appName;
	private String updateTitle;
	private String updateMessage;
	private String updateVersion;
	private boolean isUpdateCompulsory;
	private List<String> whatsNewList;
	private List<String> improvementList;

	public AndroidAppDto() {
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUpdateTitle() {
		return updateTitle;
	}

	public void setUpdateTitle(String updateTitle) {
		this.updateTitle = updateTitle;
	}

	public String getUpdateMessage() {
		return updateMessage;
	}

	public void setUpdateMessage(String updateMessage) {
		this.updateMessage = updateMessage;
	}

	public String getUpdateVersion() {
		return updateVersion;
	}

	public void setUpdateVersion(String updateVersion) {
		this.updateVersion = updateVersion;
	}

	public boolean isUpdateCompulsory() {
		return isUpdateCompulsory;
	}

	public void setUpdateCompulsory(boolean isUpdateCompulsory) {
		this.isUpdateCompulsory = isUpdateCompulsory;
	}

	public List<String> getWhatsNewList() {
		return whatsNewList;
	}

	public void setWhatsNewList(List<String> whatsNewList) {
		this.whatsNewList = whatsNewList;
	}


	public List<String> getImprovementList() {
		return improvementList;
	}

	public void setImprovementList(List<String> improvementList) {
		this.improvementList = improvementList;
	}

}
