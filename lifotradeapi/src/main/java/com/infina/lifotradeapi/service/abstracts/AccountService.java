package com.infina.lifotradeapi.service.abstracts;

import java.util.List;

import com.infina.lifotradeapi.dto.AccountDto;
import com.infina.lifotradeapi.model.Account;

public interface AccountService {

	AccountDto save(AccountDto accountDto);

	AccountDto getById(Long id);

	List<AccountDto> getAll();

	Account findById(Long id);

	AccountDto updateAccountName(Long accountId, String name);

}
