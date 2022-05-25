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

import com.mazzee.dts.dto.InvoiceDto;
import com.mazzee.dts.entity.Invoice;

/**
 * @author Admin
 *
 */
@Service
public class InvoiceDtoMapper {
	private final DtsModelMapper dtsModelMapper;

	@Autowired
	public InvoiceDtoMapper(DtsModelMapper dtsModelMapper) {
		this.dtsModelMapper = dtsModelMapper;
	}

	private ModelMapper getInvoiceToDtoMapper() {

		ModelMapper modelMapper = new ModelMapper();
		Converter<LocalDateTime, String> dateConverter = dtsModelMapper.getDateConverter();
		Converter<LocalDateTime, String> timeConverter = dtsModelMapper.getTimeConverter();
		modelMapper.typeMap(Invoice.class, InvoiceDto.class).addMappings(mapper -> {
			mapper.map(Invoice::getInvoiceId, InvoiceDto::setInvoiceId);
			mapper.using(dateConverter).map(Invoice::getInvoiceDate, InvoiceDto::setInvoiceDate);
			mapper.using(timeConverter).map(Invoice::getInvoiceDate, InvoiceDto::setInvoiceTime);
			mapper.map(Invoice::getBillAmount, InvoiceDto::setBillAmount);
			mapper.map(Invoice::getDiscountedAmount, InvoiceDto::setDiscountedAmount);
			mapper.map(Invoice::getAdvance, InvoiceDto::setAdvance);
			mapper.map(Invoice::getTotalAmount, InvoiceDto::setTotalAmount);
			mapper.map(Invoice::getPaidAmount, InvoiceDto::setPaidAmount);
		});
		return modelMapper;
	}

	public InvoiceDto getInvoiceDto(Invoice invoice) {
		ModelMapper modelMapper = getInvoiceToDtoMapper();
		InvoiceDto invoiceDto = null;
		if (Objects.nonNull(invoice)) {
			invoiceDto = modelMapper.map(invoice, InvoiceDto.class);
		}
		return invoiceDto;
	}
}
