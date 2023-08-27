package com.infina.lifotradeapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infina.lifotradeapi.apidocs.AccountTransactionControllerApiDoc;
import com.infina.lifotradeapi.dto.AccountDto;
import com.infina.lifotradeapi.dto.AccountTransactionDto;
import com.infina.lifotradeapi.dto.request.AccountTransactionSaveRequest;
import com.infina.lifotradeapi.service.abstracts.AccountTransactionService;
import com.infina.lifotradeapi.util.BindingResultChecker;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/account-transaction")
@RequiredArgsConstructor
public class AccountTransactionController implements AccountTransactionControllerApiDoc {

	private final AccountTransactionService accountTransactionService;
	private final ModelMapper modelMapper;
	private final BindingResultChecker bindingResultChecker;

	@Override
	@PostMapping
	public ResponseEntity<AccountTransactionDto> save(@Valid @RequestBody AccountTransactionSaveRequest request,
			BindingResult bindingResult) {

		bindingResultChecker.checkBindingResult(bindingResult);
		AccountTransactionDto accountTransactionDto = modelMapper.map(request, AccountTransactionDto.class);
		AccountDto accountDto = new AccountDto();
		accountDto.setId(request.getAccountId());
		accountTransactionDto.setAccount(accountDto);
		return ResponseEntity.ok(accountTransactionService.save(accountTransactionDto));
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<AccountTransactionDto> getById(@PathVariable Long id) {
		return ResponseEntity.ok(accountTransactionService.getById(id));
	}

	@Override
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<AccountTransactionDto>> getAllByCustomerId(@PathVariable Long customerId) {
		return ResponseEntity.ok(accountTransactionService.getAllByCustomerId(customerId));
	}
	
	@Override
	@GetMapping("/account/{accountId}")
	public ResponseEntity<List<AccountTransactionDto>> getAllByAccountId(@PathVariable Long accountId) {
		return ResponseEntity.ok(accountTransactionService.getAllByAccountId(accountId));
	}

	@Override
	@GetMapping
	public ResponseEntity<List<AccountTransactionDto>> getAll() {
		return ResponseEntity.ok(accountTransactionService.getAll());
	}

}
