package com.infina.lifotradeapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infina.lifotradeapi.apidocs.IndividualCustomerControllerApiDoc;
import com.infina.lifotradeapi.dto.IndividualCustomerDto;
import com.infina.lifotradeapi.dto.request.IndividualCustomerSaveRequestDto;
import com.infina.lifotradeapi.service.abstracts.IndividualCustomerService;
import com.infina.lifotradeapi.util.BindingResultChecker;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/individual-customer")
@RequiredArgsConstructor
public class IndividualCustomerController implements IndividualCustomerControllerApiDoc {

	private final IndividualCustomerService individualCustomerService;
	private final ModelMapper modelMapper;
	private final BindingResultChecker bindingResultChecker;

	@Override
	@PostMapping
	public ResponseEntity<IndividualCustomerDto> save(
			@Valid @RequestBody IndividualCustomerSaveRequestDto individualCustomerSaveRequestDto,
			BindingResult bindingResult) {
		
		bindingResultChecker.checkBindingResult(bindingResult);

		IndividualCustomerDto individualCustomerDto = modelMapper.map(individualCustomerSaveRequestDto,
				IndividualCustomerDto.class);
		return ResponseEntity.ok(individualCustomerService.save(individualCustomerDto));
	}

	@Override
	@GetMapping("/{individualCustomerId}")
	public ResponseEntity<IndividualCustomerDto> getById(@PathVariable Long individualCustomerId) {
		return ResponseEntity.ok(individualCustomerService.getById(individualCustomerId));
	}

	@Override
	@GetMapping
	public ResponseEntity<List<IndividualCustomerDto>> getAll() {
		return ResponseEntity.ok(individualCustomerService.getAll());
	}

	@Override
	@PutMapping("/{customerId}")
	public ResponseEntity<IndividualCustomerDto> updateIndividualCustomer(@PathVariable Long customerId,
			@RequestBody @Valid IndividualCustomerSaveRequestDto updateRequest, BindingResult bindingResult) {
		
		bindingResultChecker.checkBindingResult(bindingResult);
		
		return ResponseEntity.ok(individualCustomerService.updateIndividualCustomer(customerId,
				updateRequest));
	}

}
