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

import com.infina.lifotradeapi.apidocs.CorporateCustomerControllerApiDoc;
import com.infina.lifotradeapi.dto.CorporateCustomerDto;
import com.infina.lifotradeapi.dto.request.CorporateCustomerSaveRequestDto;
import com.infina.lifotradeapi.service.abstracts.CorporateCustomerService;
import com.infina.lifotradeapi.util.BindingResultChecker;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/corporate-customer")
@RequiredArgsConstructor
public class CorporateCustomerController implements CorporateCustomerControllerApiDoc {

	private final CorporateCustomerService corporateCustomerService;
	private final ModelMapper modelMapper;
	private final BindingResultChecker bindingResultChecker;

	@Override
	@PostMapping
	public ResponseEntity<CorporateCustomerDto> save(
			@Valid @RequestBody CorporateCustomerSaveRequestDto corporateCustomerSaveRequestDto,
			BindingResult bindingResult) {
		
		bindingResultChecker.checkBindingResult(bindingResult);
		return ResponseEntity.ok(corporateCustomerService
				.save(modelMapper.map(corporateCustomerSaveRequestDto, CorporateCustomerDto.class)));

	}

	@Override
	@GetMapping("/{corporateCustomerId}")
	public ResponseEntity<CorporateCustomerDto> getById(@PathVariable Long corporateCustomerId) {
		return ResponseEntity.ok(corporateCustomerService.getById(corporateCustomerId));
	}

	@Override
	@GetMapping
	public ResponseEntity<List<CorporateCustomerDto>> getAll() {
		return ResponseEntity.ok(corporateCustomerService.getAll());
	}

	@Override
	@PutMapping("/{customerId}")
	public ResponseEntity<CorporateCustomerDto> updateCorporateCustomer(@PathVariable Long customerId,
			@Valid @RequestBody CorporateCustomerSaveRequestDto updateRequest, BindingResult bindingResult) {

		bindingResultChecker.checkBindingResult(bindingResult);

		CorporateCustomerDto updatedCorporateCustomer = corporateCustomerService.updateCorporateCustomer(customerId,
				updateRequest);
		return ResponseEntity.ok(updatedCorporateCustomer);
	}

}
