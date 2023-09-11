/**
 * 
 */
package com.mazzee.dts.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.CustomerDto;
import com.mazzee.dts.entity.Customer;
import com.mazzee.dts.entity.User;
import com.mazzee.dts.mapper.CustomerDtoMapper;
import com.mazzee.dts.repo.CustomerRepo;
import com.mazzee.dts.utils.DtsUtils;

/**
 * @author Admin
 *
 */
@Service
public class CustomerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
	private final CustomerRepo customerRepository;
	private CustomerDtoMapper customerDtoMapper;

	@Autowired
	public CustomerService(CustomerRepo customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Autowired
	public void setCustomerDtoMapper(CustomerDtoMapper customerDtoMapper) {
		this.customerDtoMapper = customerDtoMapper;
	}

	public Optional<Customer> getCustomerById(int customerId) {
		LOGGER.info("get customer by id {}", customerId);
		return customerRepository.findById(customerId);
	}

	public CustomerDto getCustomerDto(Customer customer) {
		LOGGER.info("converting customer entity to dto ");
		CustomerDto customerDto = null;
		if (Objects.nonNull(customer)) {
			customerDto = customerDtoMapper.getCustomerDto(customer);
		}
		return customerDto;
	}

	public Customer updateCustomer(CustomerDto customerDto, User user) {
		Customer customer = null;
		if (Objects.nonNull(customerDto)) {
			Optional<Customer> optionalCustomer = getCustomerById(customerDto.getCustomerId());
			if (optionalCustomer.isPresent()) {
				LOGGER.info("Updating Customer details");
				customer = getUpdatedCustomer(optionalCustomer.get(), customerDto, user);
			} else {
				LOGGER.info("Adding new Customer details for user {}", user.getUserId());
				customer = getUpdatedCustomer(null, customerDto, user);
			}
			customer = customerRepository.save(customer);
		}
		return customer;
	}

	private Customer getUpdatedCustomer(Customer customer, CustomerDto customerDto, User user) {
		if (Objects.isNull(customer)) {
			customer = new Customer();
			customer.setCreatedAt(LocalDateTime.now());
		}
		customer.setFirstName(customerDto.getFirstName());
		customer.setMiddleName(customerDto.getMiddleName());
		customer.setLastName(customer.getLastName());
		customer.setMobileNo(customerDto.getMobileNo());
		customer.setEmail(customerDto.getEmail());
		customer.setGender(customerDto.getGender());
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
		if (!DtsUtils.isNullOrEmpty(customerDto.getOrderDate())) {
			LocalDate parsedOrderDate = LocalDate.parse(customerDto.getOrderDate(), dateTimeFormatter);
			LocalDateTime orderDate = LocalDateTime.of(parsedOrderDate, LocalTime.now());
			customer.setOrderDate(orderDate);
		}
		if (!DtsUtils.isNullOrEmpty(customerDto.getDeliveryDate())) {
			LocalDate parsedDeliveryDate = LocalDate.parse(customerDto.getDeliveryDate(), dateTimeFormatter);
			LocalDateTime deliveryDate = LocalDateTime.of(parsedDeliveryDate, LocalTime.now());
			customer.setDeliveryDate(deliveryDate);
		}
		customer.setUpdateAt(LocalDateTime.now());
		customer.setUser(user);
		return customer;
	}
}
