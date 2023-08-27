package com.infina.lifotradeapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infina.lifotradeapi.apidocs.AccountFundApiDoc;
import com.infina.lifotradeapi.dto.AccountFundDto;
import com.infina.lifotradeapi.dto.request.AccountFundIdRequest;
import com.infina.lifotradeapi.service.abstracts.AccountFundService;
import com.infina.lifotradeapi.util.BindingResultChecker;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/account-fund")
@RequiredArgsConstructor
public class AccountFundController implements AccountFundApiDoc {

	private final AccountFundService accountFundService;
	private final BindingResultChecker bindingResultChecker;

	@Override
	@PostMapping
	public ResponseEntity<AccountFundDto> getByAccountFundId(@Valid @RequestBody AccountFundIdRequest accountFundIdRequest,
			BindingResult bindingResult) {
		bindingResultChecker.checkBindingResult(bindingResult);
		return ResponseEntity.ok(accountFundService.getById(accountFundIdRequest));
	}

	@Override
	@GetMapping("/{accountId}")
	public ResponseEntity<List<AccountFundDto>> getAllByAccountId(@PathVariable Long accountId) {
		return ResponseEntity.ok(accountFundService.getAllByAccountId(accountId));
	}

}
