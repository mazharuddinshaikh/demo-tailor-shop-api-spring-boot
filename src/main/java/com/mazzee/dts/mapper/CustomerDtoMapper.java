/**
 * 
 */
package com.mazzee.dts.mapper;

import java.time.LocalDateTime;
import java.util.Objects;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.CustomerDto;
import com.mazzee.dts.entity.Customer;

/**
 * @author Admin
 *
 */
@Service
public class CustomerDtoMapper {
	private final DtsModelMapper dtsModelMapper;

	@Autowired
	public CustomerDtoMapper(DtsModelMapper dtsModelMapper) {
		super();
		this.dtsModelMapper = dtsModelMapper;
	}

	private ModelMapper getCustomerToDtoMapper() {
		ModelMapper modelMapper = new ModelMapper();
		Converter<LocalDateTime, String> dateConverter = dtsModelMapper.getDateConverter();
		Converter<LocalDateTime, String> timeConverter = dtsModelMapper.getTimeConverter();

		modelMapper.typeMap(Customer.class, CustomerDto.class).addMappings(mapper -> {
			mapper.map(Customer::getCustomerId, CustomerDto::setCustomerId);
			mapper.map(Customer::getFirstName, CustomerDto::setFirstName);
			mapper.map(Customer::getMiddleName, CustomerDto::setMiddleName);
			mapper.map(Customer::getLastName, CustomerDto::setLastName);
			mapper.map(Customer::getMobileNo, CustomerDto::setMobileNo);
			mapper.map(Customer::getEmail, CustomerDto::setEmail);
			mapper.map(Customer::getGender, CustomerDto::setGender);
			mapper.using(dateConverter).map(Customer::getOrderDate, CustomerDto::setOrderDate);
			mapper.using(timeConverter).map(Customer::getOrderDate, CustomerDto::setOrderTime);
			mapper.using(dateConverter).map(Customer::getDeliveryDate, CustomerDto::setDeliveryDate);
			mapper.using(timeConverter).map(Customer::getDeliveryDate, CustomerDto::setDeliveryTime);
		});
		return modelMapper;
	}

	public CustomerDto getCustomerDto(Customer customer) {
		ModelMapper modelMapper = getCustomerToDtoMapper();
		CustomerDto customerDto = null;
		if (Objects.nonNull(customer)) {
			customerDto = modelMapper.map(customer, CustomerDto.class);
		}
		return customerDto;
	}
}
