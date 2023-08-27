package com.infina.lifotradeapi.service.abstracts;

import java.util.List;

import com.infina.lifotradeapi.dto.AccountTransactionDto;

public interface AccountTransactionService {
	
	AccountTransactionDto save(AccountTransactionDto accountTransactionDto);

	AccountTransactionDto getById(Long id);
	
	List<AccountTransactionDto> getAllByCustomerId(Long customerId);
	
	List<AccountTransactionDto> getAll();

	List<AccountTransactionDto>  getAllByAccountId(Long accountId);	

}
