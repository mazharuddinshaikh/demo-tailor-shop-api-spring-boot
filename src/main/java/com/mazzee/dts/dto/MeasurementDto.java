package com.mazzee.dts.dto;

import java.util.List;

public class MeasurementDto {
	private int measurementId;
	private List<String> measurementImageList;
	private List<String> rawDressImageList;
	private List<String> patternImageList;
	private List<String> seavedImageList;
	private String comment;
	private String createdAt;
	private String updatedAt;
	private MeasurementImageDto measurementImage;

	public MeasurementDto() {
		super();
	}

	public int getMeasurementId() {
		return measurementId;
	}

	public void setMeasurementId(int measurementId) {
		this.measurementId = measurementId;
	}

	public List<String> getMeasurementImageList() {
		return measurementImageList;
	}

	public void setMeasurementImageList(List<String> measurementImageList) {
		this.measurementImageList = measurementImageList;
	}

	public List<String> getRawDressImageList() {
		return rawDressImageList;
	}

	public void setRawDressImageList(List<String> rawDressImageList) {
		this.rawDressImageList = rawDressImageList;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public MeasurementImageDto getMeasurementImage() {
		return measurementImage;
	}

	public void setMeasurementImage(MeasurementImageDto measurementImage) {
		this.measurementImage = measurementImage;
	}

}
