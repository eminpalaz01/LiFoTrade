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

import com.infina.lifotradeapi.apidocs.AccountControllerApiDoc;
import com.infina.lifotradeapi.dto.AccountDto;
import com.infina.lifotradeapi.dto.request.AccountPutNameRequest;
import com.infina.lifotradeapi.dto.request.AccountSaveRequest;
import com.infina.lifotradeapi.service.abstracts.AccountService;
import com.infina.lifotradeapi.util.BindingResultChecker;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController implements AccountControllerApiDoc {

	private final AccountService accountService;
	private final ModelMapper modelMapper;
	private final BindingResultChecker bindingResultChecker;

	@Override
	@PostMapping
	public ResponseEntity<AccountDto> save(@Valid @RequestBody AccountSaveRequest accountSaveRequest,
			BindingResult bindingResult) {		
		bindingResultChecker.checkBindingResult(bindingResult);	
		AccountDto accountDto = modelMapper.map(accountSaveRequest, AccountDto.class);
		return ResponseEntity.ok(accountService.save(accountDto));
	}

	@Override
	@GetMapping("/{accountId}")
	public ResponseEntity<AccountDto> getById(@PathVariable Long accountId) {
		return ResponseEntity.ok(accountService.getById(accountId));
	}

	@Override
	@GetMapping
	public ResponseEntity<List<AccountDto>> getAll() {
		return ResponseEntity.ok(accountService.getAll());
	}

	@Override
	@PutMapping("/{accountId}")
	public ResponseEntity<AccountDto> updateAccountName(@PathVariable Long accountId,
			@Valid @RequestBody AccountPutNameRequest request, BindingResult bindingResult) {
		
		bindingResultChecker.checkBindingResult(bindingResult);
		AccountDto updatedAccount = accountService.updateAccountName(accountId, request.getName());
		return ResponseEntity.ok(updatedAccount);

	}

}
