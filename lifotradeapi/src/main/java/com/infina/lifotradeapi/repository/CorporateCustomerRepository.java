package com.infina.lifotradeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infina.lifotradeapi.model.CorporateCustomer;

@Repository
public interface CorporateCustomerRepository extends JpaRepository<CorporateCustomer, Long> {

	Boolean existsByTitle(String title);

	Boolean existsByTaxNumber(String taxNumber);

	CorporateCustomer findByTitle(String title);

	CorporateCustomer findByTaxNumber(String taxNumber);

}
