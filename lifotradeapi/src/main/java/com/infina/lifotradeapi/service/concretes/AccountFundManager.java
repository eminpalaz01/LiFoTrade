package com.infina.lifotradeapi.service.concretes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.infina.lifotradeapi.dto.AccountDto;
import com.infina.lifotradeapi.dto.AccountFundDto;
import com.infina.lifotradeapi.dto.FundDto;
import com.infina.lifotradeapi.dto.converter.AccountConverter;
import com.infina.lifotradeapi.dto.converter.FundConverter;
import com.infina.lifotradeapi.dto.request.AccountFundIdRequest;
import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.model.AccountFund;
import com.infina.lifotradeapi.model.AccountFundId;
import com.infina.lifotradeapi.repository.AccountFundRepository;
import com.infina.lifotradeapi.service.abstracts.AccountFundService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountFundManager implements AccountFundService {

	private final AccountFundRepository accountFundRepository;
	private final AccountConverter accountConverter;
	private final FundConverter fundConverter;
	
	@Override
	@Transactional
	public void purchaseFund(Long accountId, Long fundId, Long quantity) {
		AccountFund accountFund = accountFundRepository.findByAccountIdAndFundId(accountId, fundId);
		if (accountFund == null) {
			accountFund = new AccountFund();
			accountFund.setId(new AccountFundId(accountId,fundId));
		} 
	    accountFund.purchaseQuantity(quantity);
		accountFundRepository.save(accountFund);
	}

	@Override
	@Transactional
	public void sellFund(Long accountId, Long fundId, Long quantity) {
		AccountFund accountFund = checkSufficientQuantity(accountId, fundId, quantity);
		accountFund.sellQuantity(quantity);
		accountFundRepository.save(accountFund);
	}

	@Override
	@Transactional
    public AccountFund checkSufficientQuantity(Long accountId, Long fundId, Long quantity) {
        AccountFund accountFund = accountFundRepository.findByAccountIdAndFundId(accountId, fundId);

        if (accountFund == null || accountFund.getQuantity() < quantity) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,  Arrays.asList("Yetersiz fon adeti"));
        }
        return accountFund;
    }

	@Override
	public AccountFundDto getById(AccountFundIdRequest request) {
		AccountFund accountFund = accountFundRepository
				.findByAccountIdAndFundId(request.getAccountId(), request.getFundId());
		if (accountFund == null) {
			throw new BusinessException(HttpStatus.NOT_FOUND, Arrays.asList("Fon kaydı bulunamadı"));
		}
		AccountDto accountDto = accountConverter.convertToDto(accountFund.getAccount());
		FundDto fundDto = fundConverter.convertToDto(accountFund.getFund());
		return new AccountFundDto(accountDto, fundDto, accountFund.getQuantity());
	}

	@Override
	public List<AccountFundDto> getAllByAccountId(Long accountId) {
		List<AccountFund> accountFunds = accountFundRepository.findAllByAccountId(accountId);
	    
	    List<AccountFundDto> accountFundDtos = accountFunds.stream()
	            .map(accountFund -> new AccountFundDto(
	            		accountConverter.convertToDto(accountFund.getAccount()), 
	            		fundConverter.convertToDto(accountFund.getFund()), 
	            		accountFund.getQuantity()))
	            .collect(Collectors.toList());

	    return accountFundDtos;
	}

}
