package com.infina.lifotradeapi.service.abstracts;

import java.util.List;

import com.infina.lifotradeapi.dto.IndividualCustomerDto;
import com.infina.lifotradeapi.dto.request.IndividualCustomerSaveRequestDto;

public interface IndividualCustomerService {

	IndividualCustomerDto save(IndividualCustomerDto individualCustomerDto);

	IndividualCustomerDto getById(Long id);

	Boolean existsById(Long id);

	Boolean existsByIdentificationNumber(String identificationNumber);

	List<IndividualCustomerDto> getAll();

	IndividualCustomerDto updateIndividualCustomer(Long customerId, IndividualCustomerSaveRequestDto updateRequest);

}
