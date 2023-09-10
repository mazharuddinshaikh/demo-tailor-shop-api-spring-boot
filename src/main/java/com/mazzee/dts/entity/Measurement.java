package com.mazzee.dts.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Entity
@Table(name = "dts_measurement")
public class Measurement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "measurement_id")
	private int measurementId;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "dress_id")
	private Dress dress;
	@Column(name = "measurement_image")
	private String measurementImage;
	@Column(name = "raw_dress_image")
	private String rawDressImage;
	@Column(name = "pattern_image")
	private String patternImage;
	@Column(name = "seaved_image")
	private String seavedImage;
	@Column(name = "comment")
	private String comment;
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	@OneToMany(mappedBy = "measurement", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<MeasurementImage> measurementImageList;

	public Measurement() {
		super();
	}

	public int getMeasurementId() {
		return measurementId;
	}

	public void setMeasurementId(int measurementId) {
		this.measurementId = measurementId;
	}

	public Dress getDress() {
		return dress;
	}

	public void setDress(Dress dress) {
		this.dress = dress;
	}

	public String getMeasurementImage() {
		return measurementImage;
	}

	public void setMeasurementImage(String measurementImage) {
		this.measurementImage = measurementImage;
	}

	public String getRawDressImage() {
		return rawDressImage;
	}

	public void setRawDressImage(String rawDressImage) {
		this.rawDressImage = rawDressImage;
	}

	public String getPatternImage() {
		return patternImage;
	}

	public void setPatternImage(String patternImage) {
		this.patternImage = patternImage;
	}

	public String getSeavedImage() {
		return seavedImage;
	}

	public void setSeavedImage(String seavedImage) {
		this.seavedImage = seavedImage;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<MeasurementImage> getMeasurementImageList() {
		return measurementImageList;
	}

	public void setMeasurementImageList(List<MeasurementImage> measurementImageList) {
		this.measurementImageList = measurementImageList;
	}

}
