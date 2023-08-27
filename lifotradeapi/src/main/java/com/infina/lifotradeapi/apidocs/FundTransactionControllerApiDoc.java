package com.infina.lifotradeapi.apidocs;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import com.infina.lifotradeapi.dto.FundTransactionDto;
import com.infina.lifotradeapi.dto.request.FundTransactionSaveRequestDto;

public interface FundTransactionControllerApiDoc {

	ResponseEntity<FundTransactionDto> save(@Valid @RequestBody FundTransactionSaveRequestDto request
			,BindingResult bindingResult);
	
	ResponseEntity<List<FundTransactionDto>> getAllFunds();

	ResponseEntity<FundTransactionDto> getFundById(Long fundTransactionId);

	ResponseEntity<List<FundTransactionDto>> getAllFundsByAccountId(Long accountId);

	ResponseEntity<List<FundTransactionDto>> getAllByCustomerId(Long customerId);

}
