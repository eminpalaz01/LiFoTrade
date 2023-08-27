package com.infina.lifotradeapi.dto.converter;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.infina.lifotradeapi.dto.AccountDto;
import com.infina.lifotradeapi.model.Account;

@Component
public class AccountConverter {
	
	public AccountDto convertToDto(Account account) {
        return new AccountDto(account.getId(), 
        		account.getName(),
                account.getBalance(), 
                account.getCreationDate(), 
                account.getUpdateDate(), 
                null, 
                new ArrayList<>(),
                new ArrayList<>());    
    }
    
    public Account convertToEntity(AccountDto accountDto) {
        return new Account(accountDto.getId(), 
        		accountDto.getName().trim(),
                accountDto.getBalance(), 
                null, 
                accountDto.getUpdateDate(), 
                null,
                new ArrayList<>(),
                new ArrayList<>());  
    }
}
