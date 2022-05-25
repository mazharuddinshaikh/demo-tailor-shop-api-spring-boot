/**
 * 
 */
package com.mazzee.dts.dto;

/**
 * @author Admin
 *
 */
public class DressDto {
	private int dressId;
//	private int dressTypeId;
//	private String dressTypeName;
//	private String dressTypeDescription;
	private DressTypeDto dressType;
	private CustomerDto customer;
	private String orderDate;
	private String orderTime;
	private String deliveryDate;
	private String deliveryTime;
	private String deliveryStatus;
	private int numberOfDress;
	private double price;
	private double discountedPrice;
	private String paymentStatus;
	private String comment;
	private MeasurementDto measurement;
//	private String seavedImage;
//	private String PatternImage;
//	private String rawImage;
//	private String measurementImage;

	public DressDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getDressId() {
		return dressId;
	}

	public void setDressId(int dressId) {
		this.dressId = dressId;
	}

//	public int getDressTypeId() {
//		return dressTypeId;
//	}
//
//	public void setDressTypeId(int dressTypeId) {
//		this.dressTypeId = dressTypeId;
//	}
//
//	public String getDressTypeName() {
//		return dressTypeName;
//	}
//
//	public void setDressTypeName(String dressTypeName) {
//		this.dressTypeName = dressTypeName;
//	}
//
//	public String getDressTypeDescription() {
//		return dressTypeDescription;
//	}
//
//	public void setDressTypeDescription(String dressTypeDescription) {
//		this.dressTypeDescription = dressTypeDescription;
//	}

	public CustomerDto getCustomer() {
		return customer;
	}

	public DressTypeDto getDressType() {
		return dressType;
	}

	public void setDressType(DressTypeDto dressType) {
		this.dressType = dressType;
	}

	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public int getNumberOfDress() {
		return numberOfDress;
	}

	public void setNumberOfDress(int numberOfDress) {
		this.numberOfDress = numberOfDress;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

//	public String getSeavedImage() {
//		return seavedImage;
//	}
//
//	public void setSeavedImage(String seavedImage) {
//		this.seavedImage = seavedImage;
//	}
//
//	public String getPatternImage() {
//		return PatternImage;
//	}
//
//	public void setPatternImage(String patternImage) {
//		PatternImage = patternImage;
//	}
//
//	public String getRawImage() {
//		return rawImage;
//	}
//
//	public void setRawImage(String rawImage) {
//		this.rawImage = rawImage;
//	}
//
//	public String getMeasurementImage() {
//		return measurementImage;
//	}
//
//	public void setMeasurementImage(String measurementImage) {
//		this.measurementImage = measurementImage;
//	}

	public MeasurementDto getMeasurement() {
		return measurement;
	}

	public void setMeasurement(MeasurementDto measurement) {
		this.measurement = measurement;
	}

}
