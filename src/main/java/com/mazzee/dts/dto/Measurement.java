package com.mazzee.dts.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "dts_measurement")
@JsonIgnoreProperties({ "dress", "measurementImage", "rawDressImage", "patternImage", "seavedImage" })
public class Measurement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "measurement_id")
	private int measurementId;
	@ManyToOne(fetch = FetchType.EAGER)
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
	@Transient
	List<String> rawDressImageList;
	@Transient
	List<String> patternImageList;
	@Transient
	List<String> measurementImageList;
	@Transient
	List<String> seavedImageList;
	@Transient
	private static Pattern pattern;

	public Measurement() {
		super();
		pattern = Pattern.compile("\\|\\|");
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

	public List<String> getRawDressImageList() {
		if (Objects.nonNull(this.rawDressImage)) {
			Stream<String> result = pattern.splitAsStream(this.rawDressImage);
			return result.collect(Collectors.toList());
		}
		return new ArrayList<String>();
	}

	public List<String> getPatternImageList() {
		if (Objects.nonNull(this.patternImage)) {
			Stream<String> result = pattern.splitAsStream(this.patternImage);
			return result.collect(Collectors.toList());
		}
		return new ArrayList<String>();
	}

	public List<String> getMeasurementImageList() {
		if (Objects.nonNull(this.measurementImage)) {
			Stream<String> result = pattern.splitAsStream(this.measurementImage);
			return result.collect(Collectors.toList());
		}
		return new ArrayList<String>();
	}

	public List<String> getSeavedImageList() {
		if (Objects.nonNull(this.seavedImage)) {
			Stream<String> result = pattern.splitAsStream(this.seavedImage);
			return result.collect(Collectors.toList());
		}
		return new ArrayList<String>();
	}

}
