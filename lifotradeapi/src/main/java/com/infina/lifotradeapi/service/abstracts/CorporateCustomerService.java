package com.infina.lifotradeapi.service.abstracts;

import java.util.List;

import com.infina.lifotradeapi.dto.CorporateCustomerDto;
import com.infina.lifotradeapi.dto.request.CorporateCustomerSaveRequestDto;

public interface CorporateCustomerService {

	CorporateCustomerDto save(CorporateCustomerDto corporateCustomerDto);

	CorporateCustomerDto getById(Long id);

	Boolean existsById(Long id);

	Boolean existsByTaxNumber(String taxNumber);

	List<CorporateCustomerDto> getAll();

	CorporateCustomerDto updateCorporateCustomer(Long customerId, CorporateCustomerSaveRequestDto updateRequest);

}
