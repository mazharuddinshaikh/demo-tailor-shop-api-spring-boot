package com.mazzee.dts.dto;

public class UserDressTypeDto {
	private int userDressTypeId;
	private String typeName;
	private String typeDescription;
	private String comment;
	private String createdAt;
	private String updatedAt;
	private int createdBy;
	private double price;

	public UserDressTypeDto() {
		super();
	}

	public int getUserDressTypeId() {
		return userDressTypeId;
	}

	public void setUserDressTypeId(int userDressTypeId) {
		this.userDressTypeId = userDressTypeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
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

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
