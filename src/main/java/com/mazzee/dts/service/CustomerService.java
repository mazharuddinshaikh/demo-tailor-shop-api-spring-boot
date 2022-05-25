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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.CustomerDto;
import com.mazzee.dts.entity.Customer;
import com.mazzee.dts.mapper.CustomerDtoMapper;
import com.mazzee.dts.repo.CustomerRepo;

/**
 * @author Admin
 *
 */
@Service
public class CustomerService {
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
		Optional<Customer> customer = customerRepository.findById(customerId);
		return customer;
	}

	public CustomerDto getCustomerDto(Customer customer) {
		CustomerDto customerDto = null;
		if (Objects.nonNull(customer)) {
			customerDto = customerDtoMapper.getCustomerDto(customer);
		}
		return customerDto;
	}

	public Customer updateCustomer(CustomerDto customerDto) {
		Customer customer = null;
		if (Objects.nonNull(customerDto)) {
			Optional<Customer> optionalCustomer = getCustomerById(customerDto.getCustomerId());
			if (optionalCustomer.isPresent()) {
				customer = optionalCustomer.get();
			}

			if (Objects.nonNull(customer)) {
				customer.setFirstName(customerDto.getFirstName());
				customer.setMiddleName(customerDto.getMiddleName());
				customer.setLastName(customer.getLastName());
				customer.setMobileNo(customerDto.getMobileNo());
				customer.setEmail(customerDto.getEmail());
				customer.setGender(customerDto.getGender());
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
				LocalDate parsedOrderDate = LocalDate.parse(customerDto.getOrderDate(), dateTimeFormatter);
				LocalDateTime orderDate = LocalDateTime.of(parsedOrderDate, LocalTime.now());
				customer.setOrderDate(orderDate);
				LocalDate parsedDeliveryDate = LocalDate.parse(customerDto.getDeliveryDate(), dateTimeFormatter);
				LocalDateTime deliveryDate = LocalDateTime.of(parsedDeliveryDate, LocalTime.now());
				customer.setDeliveryDate(deliveryDate);
				customer.setUpdateAt(LocalDateTime.now());
				customer = customerRepository.save(customer);
			}
		}
		return customer;
	}
}
