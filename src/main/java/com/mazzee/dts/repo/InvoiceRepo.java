package com.mazzee.dts.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mazzee.dts.entity.Invoice;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Integer> {
	@Query(value = "SELECT * FROM DTS_INVOICE WHERE CUSTOMER_ID = 1000", nativeQuery = true)
	Optional<Invoice> getInvoiceByCustomerId(@Param("customerId") int customerId);
}
