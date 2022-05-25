package com.mazzee.dts.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mazzee.dts.entity.Customer;

/**
 * @author Admin
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

}
