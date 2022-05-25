/**
 * 
 */
package com.mazzee.dts.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mazzee.dts.dto.InvoiceDto;
import com.mazzee.dts.entity.Invoice;
import com.mazzee.dts.mapper.InvoiceDtoMapper;
import com.mazzee.dts.repo.InvoiceRepo;

/**
 * @author Admin
 *
 */
@Service
public class InvoiceService {
	private InvoiceRepo invoiceRepository;
	private InvoiceDtoMapper invoiceDtoMapper;

	@Autowired
	public void setInvoiceDtoMapper(InvoiceDtoMapper invoiceDtoMapper) {
		this.invoiceDtoMapper = invoiceDtoMapper;
	}

	@Autowired
	public InvoiceService(InvoiceRepo invoiceRepository) {
		this.invoiceRepository = invoiceRepository;
	};

	public Optional<Invoice> getInvoiceByCustomerId(int customerId) {
		return invoiceRepository.getInvoiceByCustomerId(customerId);
	}

	public InvoiceDto getInvoiceDto(Invoice invoice) {
		InvoiceDto invoiceDto = null;
		if (Objects.nonNull(invoice)) {
			invoiceDto = invoiceDtoMapper.getInvoiceDto(invoice);
		}
		return invoiceDto;
	}

	public Invoice updateInvoice(InvoiceDto invoiceDto, int customerId) {
		Invoice invoice = null;
		if (Objects.nonNull(invoiceDto)) {
			Optional<Invoice> optionalInvoice = getInvoiceByCustomerId(customerId);
			if (optionalInvoice.isPresent()) {
				invoice = optionalInvoice.get();
				if (Objects.nonNull(invoice)) {
					invoice.setBillAmount(invoiceDto.getBillAmount());
					invoice.setDiscountedAmount(invoiceDto.getDiscountedAmount());
					invoice.setAdvance(invoiceDto.getAdvance());
					invoice.setTotalAmount(invoiceDto.getTotalAmount());
					invoice.setPaidAmount(invoiceDto.getPaidAmount());
					invoice = invoiceRepository.save(invoice);
				}
			}

		}
		return invoice;
	}

}
