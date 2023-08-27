package com.infina.lifotradeapi.dto.converter;

import org.springframework.stereotype.Component;

import com.infina.lifotradeapi.dto.AccountFundDto;
import com.infina.lifotradeapi.model.AccountFund;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccountFundConverter {
	private final AccountConverter accountConverter;
	private final FundConverter fundConverter;
	
	public AccountFundDto convertToDto(AccountFund accountFund) {
		return new AccountFundDto( 
				accountConverter.convertToDto(accountFund.getAccount()), 
				fundConverter.convertToDto(accountFund.getFund()),
				accountFund.getQuantity()) ;
	}

	public AccountFund convertToEntity(AccountFundDto accountFundDto) {
		return new AccountFund(null, 
				accountConverter.convertToEntity(accountFundDto.getAccountDto()) , 
				fundConverter.convertToEntity(accountFundDto.getFundDto()) ,
				accountFundDto.getQuantity());
	}
}
