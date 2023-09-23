package com.mazzee.dts.dto;

import java.util.List;

public class MeasurementImageDto {
	private List<String> measurementImageList;
	private List<String> rawImageList;
	private List<String> patternImageList;
	private List<String> seavedImageList;
	public List<String> getMeasurementImageList() {
		return measurementImageList;
	}
	public void setMeasurementImageList(List<String> measurementImageList) {
		this.measurementImageList = measurementImageList;
	}
	public List<String> getRawImageList() {
		return rawImageList;
	}
	public void setRawImageList(List<String> rawImageList) {
		this.rawImageList = rawImageList;
	}
	public List<String> getPatternImageList() {
		return patternImageList;
	}
	public void setPatternImageList(List<String> patternImageList) {
		this.patternImageList = patternImageList;
	}
	public List<String> getSeavedImageList() {
		return seavedImageList;
	}
	public void setSeavedImageList(List<String> seavedImageList) {
		this.seavedImageList = seavedImageList;
	}

	
}
