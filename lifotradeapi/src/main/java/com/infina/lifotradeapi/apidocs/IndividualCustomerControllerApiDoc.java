package com.infina.lifotradeapi.apidocs;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.infina.lifotradeapi.dto.IndividualCustomerDto;
import com.infina.lifotradeapi.dto.request.IndividualCustomerSaveRequestDto;

public interface IndividualCustomerControllerApiDoc {

	public ResponseEntity<IndividualCustomerDto> save(
			@RequestBody IndividualCustomerSaveRequestDto individualCustomerSaveRequestDto,
			BindingResult bindingResult);

	public ResponseEntity<IndividualCustomerDto> getById(@PathVariable Long individualCustomerId);

	public ResponseEntity<List<IndividualCustomerDto>> getAll();

	ResponseEntity<IndividualCustomerDto> updateIndividualCustomer(Long customerId,
			@Valid IndividualCustomerSaveRequestDto updateRequest, BindingResult bindingResult);
}
