package com.infina.lifotradeapi.service.abstracts;

import java.util.List;

import com.infina.lifotradeapi.dto.AccountFundDto;
import com.infina.lifotradeapi.dto.request.AccountFundIdRequest;
import com.infina.lifotradeapi.model.AccountFund;

public interface AccountFundService {

	public void purchaseFund(Long accountId, Long fundId, Long quantity);
	
	public void sellFund(Long accountId, Long fundId, Long quantity);
	
	public AccountFund checkSufficientQuantity(Long accountId, Long fundId, Long quantity);
	
	public AccountFundDto getById(AccountFundIdRequest request);
	
	public List<AccountFundDto> getAllByAccountId(Long accountId);
}
