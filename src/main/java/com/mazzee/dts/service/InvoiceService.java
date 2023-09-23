/**
 * 
 */
package com.mazzee.dts.service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.InvoiceDto;
import com.mazzee.dts.entity.Customer;
import com.mazzee.dts.entity.Invoice;
import com.mazzee.dts.mapper.InvoiceDtoMapper;
import com.mazzee.dts.repo.InvoiceRepo;

/**
 * @author Admin
 *
 */
@Service
public class InvoiceService {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);
	private InvoiceRepo invoiceRepository;
	private InvoiceDtoMapper invoiceDtoMapper;

	@Autowired
	public void setInvoiceDtoMapper(InvoiceDtoMapper invoiceDtoMapper) {
		this.invoiceDtoMapper = invoiceDtoMapper;
	}

	@Autowired
	public InvoiceService(InvoiceRepo invoiceRepository) {
		this.invoiceRepository = invoiceRepository;
	}

	public Optional<Invoice> getInvoiceByCustomerId(int customerId) {
		LOGGER.info("Get invoice of customer {}", customerId);
		return invoiceRepository.getInvoiceByCustomerId(customerId);
	}

	public InvoiceDto getInvoiceDto(Invoice invoice) {
		LOGGER.info("converting invoice entity to dto");
		InvoiceDto invoiceDto = null;
		if (Objects.nonNull(invoice)) {
			invoiceDto = invoiceDtoMapper.getInvoiceDto(invoice);
		}
		return invoiceDto;
	}

	public Invoice updateInvoice(InvoiceDto invoiceDto, Customer customer) {
		Invoice invoice = null;
		if (Objects.nonNull(invoiceDto)) {
			Optional<Invoice> optionalInvoice = getInvoiceByCustomerId(customer.getCustomerId());
			if (optionalInvoice.isPresent()) {
				LOGGER.info("Updating invoice details for customer {}", customer.getCustomerId());
				invoice = getUpdatedInvoice(optionalInvoice.get(), invoiceDto, customer);
			} else {
				LOGGER.info("Adding new invoice details for customer {}", customer.getCustomerId());
				invoice = getUpdatedInvoice(null, invoiceDto, customer);
			}
			invoice = invoiceRepository.save(invoice);
		}
		return invoice;
	}

	private Invoice getUpdatedInvoice(Invoice invoice, InvoiceDto invoiceDto, Customer customer) {
		if (Objects.isNull(invoice)) {
			invoice = new Invoice();
			invoice.setInvoiceDate(LocalDateTime.now());
		}
		double billAmount = invoiceDto.getBillAmount();
		double discountedAmount = invoiceDto.getDiscountedAmount();
		double advancedAmount = invoiceDto.getAdvance();
		double totalAmount = invoiceDto.getTotalAmount();
		double paidAmount = invoiceDto.getPaidAmount();
		if (discountedAmount <= 0) {
			discountedAmount = billAmount;
		}
		if (totalAmount <= 0) {
			totalAmount = discountedAmount;
		}
		if (paidAmount <= 0) {
			paidAmount = advancedAmount;
		}
		invoice.setBillAmount(billAmount);
		invoice.setDiscountedAmount(discountedAmount);
		invoice.setAdvance(advancedAmount);
		invoice.setTotalAmount(totalAmount);
		invoice.setPaidAmount(paidAmount);
		invoice.setCustomer(customer);
		return invoice;
	}
}
