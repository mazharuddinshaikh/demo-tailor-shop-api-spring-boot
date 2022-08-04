package com.mazzee.dts.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Entity
@Table(name = "dts_dress")
public class Dress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dress_id")
	private int dressId;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "dress_type")
	private UserDressType userDressType;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id")
	private Customer customer;
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
	@OneToOne(mappedBy = "dress", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Measurement measurement;

	public Dress() {
		super();
	}

	public int getDressId() {
		return dressId;
	}

	public void setDressId(int dressId) {
		this.dressId = dressId;
	}

	public UserDressType getUserDressType() {
		return userDressType;
	}

	public void setUserDressType(UserDressType userDressType) {
		this.userDressType = userDressType;
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

	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

	@Override
	public String toString() {
		return "Dress [dressId=" + dressId + ", dressType=" + userDressType + ", customer=" + customer + ", orderDate="
				+ orderDate + ", deliveryDate=" + deliveryDate + ", deliveryStatus=" + deliveryStatus
				+ ", numberOfDress=" + numberOfDress + ", price=" + price + ", discountedPrice=" + discountedPrice
				+ ", paymentStatus=" + paymentStatus + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", comment=" + comment + ", measurement=" + measurement + "]";
	}

}
