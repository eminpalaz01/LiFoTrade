package com.infina.lifotradeapi.apidocs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.infina.lifotradeapi.dto.AccountTransactionDto;
import com.infina.lifotradeapi.dto.request.AccountTransactionSaveRequest;

public interface AccountTransactionControllerApiDoc {
	
	ResponseEntity<AccountTransactionDto> save(AccountTransactionSaveRequest request, BindingResult bindingResult);

	ResponseEntity<AccountTransactionDto> getById(Long id);

	ResponseEntity<List<AccountTransactionDto>> getAllByCustomerId(Long customerId);

	ResponseEntity<List<AccountTransactionDto>> getAll();

	ResponseEntity<List<AccountTransactionDto>> getAllByAccountId(Long accountId);

}
