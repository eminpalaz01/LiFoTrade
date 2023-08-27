package com.infina.lifotradeapi.service.concretes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.infina.lifotradeapi.dto.AccountTransactionDto;
import com.infina.lifotradeapi.dto.converter.AccountConverter;
import com.infina.lifotradeapi.dto.converter.AccountTransactionConverter;
import com.infina.lifotradeapi.dto.converter.CustomerConverter;
import com.infina.lifotradeapi.dto.converter.EmployeeConverter;
import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.model.Account;
import com.infina.lifotradeapi.model.AccountTransaction;
import com.infina.lifotradeapi.model.Customer;
import com.infina.lifotradeapi.model.Employee;
import com.infina.lifotradeapi.repository.AccountTransactionRepository;
import com.infina.lifotradeapi.service.abstracts.AccountService;
import com.infina.lifotradeapi.service.abstracts.AccountTransactionService;
import com.infina.lifotradeapi.service.abstracts.EmployeeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountTransactionManager implements AccountTransactionService {

	private final AccountTransactionRepository accountTransactionRepository;
	private final AccountService accountService;
	private final EmployeeService employeeService;
	private final AccountTransactionConverter accountTransactionConverter;
	private final AccountConverter accountConverter;
    private final EmployeeConverter employeeConverter;
    private final CustomerConverter customerConverter;
	
	@Override
	public AccountTransactionDto save(AccountTransactionDto accountTransactionDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Employee performedBy = employeeService.findByUsername(authentication.getName());
	    switch (accountTransactionDto.getAccountTransactionType()) {
		 case DEPOSIT:
			return deposit(accountTransactionDto, performedBy);
		 case WITHDRAW:
			return withdraw(accountTransactionDto, performedBy);
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, Arrays.asList("İşlem tipiniz hatalıdır."));
	}

	@Transactional
	private AccountTransactionDto deposit(AccountTransactionDto accountTransactionDto, Employee performedBy) {
        Account account = accountService.findById(accountTransactionDto.getAccount().getId());
        account.deposit(accountTransactionDto.getAmount());
        
        AccountTransaction accountTransaction = accountTransactionConverter.convertToEntity(accountTransactionDto);        
        accountTransaction.setAccount(account);
        accountTransaction.setPerformedBy(performedBy);
        
        accountTransaction = save(accountTransaction);
        
        accountTransactionDto = convertToDto(accountTransaction);
        
		return accountTransactionDto;
	}

	@Transactional
	private AccountTransactionDto withdraw(AccountTransactionDto accountTransactionDto, Employee performedBy) {
        Account account = accountService.findById(accountTransactionDto.getAccount().getId());
        account.withdraw(accountTransactionDto.getAmount());
        
        AccountTransaction accountTransaction = accountTransactionConverter.convertToEntity(accountTransactionDto);       
        accountTransaction.setAccount(account);
        accountTransaction.setPerformedBy(performedBy);
        
        accountTransaction = save(accountTransaction);
        
        accountTransactionDto = convertToDto(accountTransaction);
        
		return accountTransactionDto;
	}
	
	private AccountTransaction save(AccountTransaction accountTransaction) {
		return accountTransactionRepository.save(accountTransaction);
	}

	@Override
	public AccountTransactionDto getById(Long id) {
		return convertToDto(findById(id));		 
	}
		
	private AccountTransaction findById(Long id) {
		return accountTransactionRepository.findById(id).orElseThrow(()
				-> new BusinessException(HttpStatus.NOT_FOUND, Arrays.asList("Hesap işlemi bulunamadı.")));	 
	}

	@Override
	public List<AccountTransactionDto> getAllByCustomerId(Long customerId) {
		return findByAccount_CustomerId(customerId)
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	private List<AccountTransaction> findByAccount_CustomerId(Long customerId) {
		return accountTransactionRepository.findByAccount_CustomerId(customerId);
	}

	@Override
	public List<AccountTransactionDto> getAll() {
		return findAll()
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	private List<AccountTransaction> findAll() {
		return accountTransactionRepository.findAll();
	}
	
	private AccountTransactionDto convertToDto(AccountTransaction accountTransaction) {
		AccountTransactionDto accountTransactionDto = accountTransactionConverter.convertToDto(accountTransaction);
        accountTransactionDto.setAccount(accountConverter.convertToDto(accountTransaction.getAccount())); 
        
        Customer customer = accountTransaction.getAccount().getCustomer();
        customer.setAccounts(new ArrayList<Account>());
        accountTransactionDto.getAccount().setCustomer(customerConverter.convertToDto(customer));
        
        accountTransactionDto.setPerformedBy(employeeConverter.convertToDto(accountTransaction.getPerformedBy()));
        return accountTransactionDto;
	}

	@Override
	public List<AccountTransactionDto> getAllByAccountId(Long accountId) {
		List<AccountTransaction> transactions = accountTransactionRepository.findByAccountId(accountId);

	    if (transactions.isEmpty()) {
	        throw new BusinessException(
	        		HttpStatus.NOT_FOUND, Arrays.asList("Verilen hesap numarasına ait işlem bulunamadı"));
	    }

	    return transactions
	    		.stream()
	            .map(this::convertToDto)
	            .collect(Collectors.toList());
	}

}
