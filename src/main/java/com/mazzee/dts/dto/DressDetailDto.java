/**
 * 
 */
package com.mazzee.dts.dto;

import java.util.List;

/**
 * @author Admin
 *
 */
public class DressDetailDto {
	private CustomerDto customer;
	private List<DressDto> dressList;
	private InvoiceDto customerInvoice;

	public DressDetailDto() {
	}

	public CustomerDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}

	public List<DressDto> getDressList() {
		return dressList;
	}

	public void setDressList(List<DressDto> dressList) {
		this.dressList = dressList;
	}

	public InvoiceDto getCustomerInvoice() {
		return customerInvoice;
	}

	public void setCustomerInvoice(InvoiceDto customerInvoice) {
		this.customerInvoice = customerInvoice;
	}

}
