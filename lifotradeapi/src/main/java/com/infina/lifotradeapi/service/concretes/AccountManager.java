package com.infina.lifotradeapi.service.concretes;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.infina.lifotradeapi.dto.AccountDto;
import com.infina.lifotradeapi.dto.CustomerDto;
import com.infina.lifotradeapi.dto.converter.AccountConverter;
import com.infina.lifotradeapi.dto.converter.CustomerConverter;
import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.model.Account;
import com.infina.lifotradeapi.repository.AccountRepository;
import com.infina.lifotradeapi.service.abstracts.AccountService;
import com.infina.lifotradeapi.service.abstracts.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountManager implements AccountService {

	private final AccountRepository accountRepository;
	private final AccountConverter accountConverter;
	private final CustomerService customerService;
	private final CustomerConverter customerConverter;

	@Override
	public AccountDto save(AccountDto accountDto) {
		CustomerDto customerDto = customerService.getById(accountDto.getCustomer().getId());

		accountDto.setBalance(BigDecimal.ZERO);
		accountDto.setCustomer(customerDto);

		Account account = convertToEntity(accountDto, customerDto);
		Account accountDb = accountRepository.save(account);

		accountDto = convertToDto(accountDb);

		return accountDto;
	}

	@Override
	public AccountDto getById(Long id) {
		Account account = findById(id);
		AccountDto accountDto = convertToDto(account);
		return accountDto;
	}

	@Override
	public List<AccountDto> getAll() {
		List<Account> accounts = findAll();
		List<AccountDto> accountsDto = accounts
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
		return accountsDto;
	}

	private List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public Account findById(Long id) {
		return accountRepository.findById(id)
				.orElseThrow(
				() -> new BusinessException(HttpStatus.NOT_FOUND,Arrays.asList("Hesap bulunamadı")));
	}

	private AccountDto convertToDto(Account account) {
		AccountDto accountDto = accountConverter.convertToDto(account);
		accountDto.setCustomer(customerConverter.convertToDto(account.getCustomer()));
		return accountDto;
	}

	public Account convertToEntity(AccountDto accountDto, CustomerDto customerDto) {
		Account account = accountConverter.convertToEntity(accountDto);
		account.setCustomer(customerConverter.convertToEntity(customerDto));
		return account;
	}

	@Override
	public AccountDto updateAccountName(Long accountId, String name) {
		Account account = findById(accountId);
		account.setName(name);
		accountRepository.save(account);
		return convertToDto(account);
	}

}
