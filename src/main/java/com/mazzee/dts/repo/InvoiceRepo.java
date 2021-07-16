package com.mazzee.dts.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mazzee.dts.dto.Invoice;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Integer> {

}
