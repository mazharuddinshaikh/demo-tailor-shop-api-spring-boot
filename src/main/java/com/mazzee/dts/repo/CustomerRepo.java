package com.mazzee.dts.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mazzee.dts.dto.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

}
