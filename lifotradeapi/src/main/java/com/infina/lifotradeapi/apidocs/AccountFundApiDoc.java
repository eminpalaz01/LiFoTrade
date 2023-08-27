package com.infina.lifotradeapi.apidocs;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import com.infina.lifotradeapi.dto.AccountFundDto;
import com.infina.lifotradeapi.dto.request.AccountFundIdRequest;

public interface AccountFundApiDoc {

	ResponseEntity<List<AccountFundDto>> getAllByAccountId(@RequestParam Long accountId);

	ResponseEntity<AccountFundDto> getByAccountFundId(@Valid AccountFundIdRequest accountFundIdRequest,
			BindingResult bindingResult);
}
