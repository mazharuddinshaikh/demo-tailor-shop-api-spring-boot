package com.mazzee.dts.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Entity
@Table(name = "dts_dress")
@JsonIgnoreProperties(value = { "dressType", "measurementList" })
public class Dress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dress_id")
	private int dressId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dress_type")
	private DressType dressType;
	@Transient
	private int dressTypeId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	private Customer customer;
//	@Transient
//	private int customerId;
	@Column(name = "order_date")
	private LocalDateTime orderDate;
	@Column(name = "delivery_date")
	private LocalDateTime deliveryDate;
	@Column(name = "delivery_status")
	private String deliveryStatus;
	@Column(name = "number_of_dress")
	private int numberOfDress;
	@Column(name = "price")
	private double price;
	@Column(name = "discounted_price")
	private double discountedPrice;
	@Column(name = "payment_status")
	private String paymentStatus;
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	@Column(name = "comment")
	private String comment;
	@OneToMany(mappedBy = "dress", fetch = FetchType.EAGER)
	private List<Measurement> measurementList;
	@Transient
	private String dressImage;

	public Dress() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getDressId() {
		return dressId;
	}

	public void setDressId(int dressId) {
		this.dressId = dressId;
	}

	public DressType getDressType() {
		return dressType;
	}

	public void setDressType(DressType dressType) {
		this.dressType = dressType;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getDressTypeId() {
		if (Objects.nonNull(this.dressType)) {
			return this.dressType.getTypeId();
		}
		return dressTypeId;
	}

	public void setDressTypeId(int dressTypeId) {
		this.dressTypeId = dressTypeId;
	}

//	public int getCustomerId() {
//		if (Objects.nonNull(this.customer)) {
//			return this.customer.getCustomerId();
//		}
//		return customerId;
//	}
//
//	public void setCustomerId(int customerId) {
//		this.customerId = customerId;
//	}

	public List<Measurement> getMeasurementList() {
		return measurementList;
	}

	public void setMeasurementList(List<Measurement> measurementList) {
		this.measurementList = measurementList;
	}

	public String getDressImage() {
		if (Objects.nonNull(measurementList) && !measurementList.isEmpty()) {
			return measurementList.get(0).getRawDressImage();
		}
		return dressImage;
	}

	public void setDressImage(String dressImage) {
		this.dressImage = dressImage;
	}

}
