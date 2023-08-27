package com.infina.lifotradeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infina.lifotradeapi.model.IndividualCustomer;

@Repository
public interface IndividualCustomerRepository extends JpaRepository<IndividualCustomer, Long> {

	Boolean existsByIdentificationNumber(String identificationNumber);

	IndividualCustomer findByIdentificationNumber(String identificationNumber);

}
