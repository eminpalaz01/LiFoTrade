package com.infina.lifotradeapi.dto.converter;

import org.springframework.stereotype.Component;

import com.infina.lifotradeapi.dto.AccountTransactionDto;
import com.infina.lifotradeapi.model.AccountTransaction;

@Component
public class AccountTransactionConverter {
	
	public AccountTransactionDto convertToDto(AccountTransaction transaction) {
		return new AccountTransactionDto(transaction.getId(), 
				transaction.getAmount(),
				null, 
				null,
				transaction.getAccountTransactionType(),
				transaction.getTransactionDate());
	}
	
	public AccountTransaction convertToEntity(AccountTransactionDto transaction) {
		return new AccountTransaction(transaction.getId(), 
				transaction.getAmount(),
				null,
				null,
				transaction.getAccountTransactionType(),
				transaction.getTransactionDate());
	}

}
