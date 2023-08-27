package com.infina.lifotradeapi.apidocs;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.infina.lifotradeapi.dto.AccountDto;
import com.infina.lifotradeapi.dto.request.AccountPutNameRequest;
import com.infina.lifotradeapi.dto.request.AccountSaveRequest;

public interface AccountControllerApiDoc {

	ResponseEntity<AccountDto> save(AccountSaveRequest accountSaveRequest, BindingResult bindingResult);

	ResponseEntity<AccountDto> getById(Long accountId);

	ResponseEntity<List<AccountDto>> getAll();

	ResponseEntity<AccountDto> updateAccountName(@PathVariable Long accountId,
			@Valid @RequestBody AccountPutNameRequest request, BindingResult bindingResult);
}
