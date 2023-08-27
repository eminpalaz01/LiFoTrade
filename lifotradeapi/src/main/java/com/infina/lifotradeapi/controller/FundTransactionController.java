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

import com.infina.lifotradeapi.apidocs.FundTransactionControllerApiDoc;
import com.infina.lifotradeapi.dto.FundTransactionDto;
import com.infina.lifotradeapi.dto.request.FundTransactionSaveRequestDto;
import com.infina.lifotradeapi.service.abstracts.FundTransactionService;
import com.infina.lifotradeapi.util.BindingResultChecker;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/fund-transaction")
@RequiredArgsConstructor
public class FundTransactionController implements FundTransactionControllerApiDoc {

    private final FundTransactionService fundTransactionService;
	private final BindingResultChecker bindingResultChecker;

    @Override
    @PostMapping
    public ResponseEntity<FundTransactionDto> save(@Valid @RequestBody FundTransactionSaveRequestDto request,BindingResult bindingResult) {
		bindingResultChecker.checkBindingResult(bindingResult);
        return ResponseEntity.ok(fundTransactionService.save(request));
    }
    
	@Override
	@GetMapping("/{fundTransactionId}")
	public ResponseEntity<FundTransactionDto> getFundById(@PathVariable Long fundTransactionId) {
		return ResponseEntity.ok(fundTransactionService.getById(fundTransactionId));
	}

	@Override
	@GetMapping
	public ResponseEntity<List<FundTransactionDto>> getAllFunds() {
		return ResponseEntity.ok(fundTransactionService.getAll());
	}
	
	@Override
	@GetMapping("/account/{accountId}")
	public ResponseEntity<List<FundTransactionDto>> getAllFundsByAccountId(@PathVariable Long accountId) {
		return ResponseEntity.ok(fundTransactionService.getAllByAccountId(accountId));
	}
	
	@Override
	@GetMapping("/customer/{customerId}")
    public ResponseEntity<List<FundTransactionDto>> getAllByCustomerId(@PathVariable Long customerId) {
        List<FundTransactionDto> transactions = fundTransactionService.getAllByCustomerId(customerId);
        return ResponseEntity.ok(transactions);
    }
}
