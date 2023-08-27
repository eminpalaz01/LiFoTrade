package com.infina.lifotradeapi.apidocs;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.infina.lifotradeapi.dto.CorporateCustomerDto;
import com.infina.lifotradeapi.dto.request.CorporateCustomerSaveRequestDto;

public interface CorporateCustomerControllerApiDoc {

	public ResponseEntity<CorporateCustomerDto> save(
			@RequestBody CorporateCustomerSaveRequestDto corporateCustomerSaveRequestDto, BindingResult bindingResult);

	public ResponseEntity<CorporateCustomerDto> getById(@PathVariable Long corporateCustomerId);

	public ResponseEntity<List<CorporateCustomerDto>> getAll();

	ResponseEntity<CorporateCustomerDto> updateCorporateCustomer(Long customerId,
			@Valid CorporateCustomerSaveRequestDto updateRequest, BindingResult bindingResult);
}
