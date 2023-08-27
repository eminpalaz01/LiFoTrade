package com.infina.lifotradeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infina.lifotradeapi.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	

}
