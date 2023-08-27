package com.infina.lifotradeapi.service.concretes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.infina.lifotradeapi.dto.AccountTransactionDto;
import com.infina.lifotradeapi.dto.FundTransactionDto;
import com.infina.lifotradeapi.dto.converter.AccountConverter;
import com.infina.lifotradeapi.dto.converter.CustomerConverter;
import com.infina.lifotradeapi.dto.converter.EmployeeConverter;
import com.infina.lifotradeapi.dto.converter.FundConverter;
import com.infina.lifotradeapi.dto.converter.FundTransactionConverter;
import com.infina.lifotradeapi.dto.request.AccountTransactionSaveRequest;
import com.infina.lifotradeapi.dto.request.FundTransactionSaveRequestDto;
import com.infina.lifotradeapi.enums.AccountTransactionType;
import com.infina.lifotradeapi.enums.FundTransactionType;
import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.model.Account;
import com.infina.lifotradeapi.model.Customer;
import com.infina.lifotradeapi.model.Employee;
import com.infina.lifotradeapi.model.Fund;
import com.infina.lifotradeapi.model.FundTransaction;
import com.infina.lifotradeapi.repository.FundTransactionRepository;
import com.infina.lifotradeapi.service.abstracts.AccountFundService;
import com.infina.lifotradeapi.service.abstracts.AccountService;
import com.infina.lifotradeapi.service.abstracts.AccountTransactionService;
import com.infina.lifotradeapi.service.abstracts.EmployeeService;
import com.infina.lifotradeapi.service.abstracts.FundService;
import com.infina.lifotradeapi.service.abstracts.FundTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FundTransactionManager implements FundTransactionService {

	private final FundTransactionRepository fundTransactionRepository;
	private final FundTransactionConverter fundTransactionConverter;
	private final ModelMapper modelMapper;
	private final AccountService accountService;
	private final EmployeeService employeeService;
	private final FundService fundService;
	private final AccountConverter accountConverter;
	private final EmployeeConverter employeeConverter;
	private final FundConverter fundConverter;
	private final CustomerConverter customerConverter;
	private final AccountTransactionService accountTransactionService;
	private final AccountFundService accountFundService;

	@Transactional
	@Override
	public FundTransactionDto save(FundTransactionSaveRequestDto fundTransactionDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Employee performedBy = employeeService.findByUsername(authentication.getName());

		switch (fundTransactionDto.getTransactionType()) {
		case PURCHASE:
			return purchaseFundRequest(fundTransactionDto, performedBy);
		case SALE:
			return saleFundRequest(fundTransactionDto, performedBy);
		}

		throw new BusinessException(HttpStatus.BAD_REQUEST, Arrays.asList("İşlem tipi hatalı."));
	}

	@Transactional
	public FundTransactionDto saleFundRequest(FundTransactionSaveRequestDto request, Employee performedBy) {
		accountFundService.sellFund(request.getAccountId(), request.getFundId(), request.getQuantity());
		Account account = accountService.findById(request.getAccountId());

		Fund fund = fundService.findById(request.getFundId());
		BigDecimal totalPrice = fund.calculatePrice(request.getQuantity());

		AccountTransactionSaveRequest transferRequest = new AccountTransactionSaveRequest(request.getAccountId(),
				AccountTransactionType.DEPOSIT, totalPrice);

		accountTransactionService.save(modelMapper.map(transferRequest, AccountTransactionDto.class));
		
		FundTransaction fundTransaction = new FundTransaction();
		fundTransaction.setAccount(account);
		fundTransaction.setPerformedBy(performedBy);
		fundTransaction.setFund(fund);
		fundTransaction.setQuantity(request.getQuantity());
		fundTransaction.setTransactionType(FundTransactionType.SALE);
		fundTransaction.setAmount(totalPrice);
		fundTransaction = fundTransactionRepository.save(fundTransaction);

		return convert(fundTransaction);
	}

	@Transactional
	public FundTransactionDto purchaseFundRequest(FundTransactionSaveRequestDto request, Employee performedBy) {
		Account account = accountService.findById(request.getAccountId());

		Fund fund = fundService.findById(request.getFundId());
		BigDecimal totalPrice = fund.calculatePrice(request.getQuantity());

		AccountTransactionSaveRequest transferRequest = new AccountTransactionSaveRequest(request.getAccountId(),
				AccountTransactionType.WITHDRAW, totalPrice);
		accountTransactionService.save(modelMapper.map(transferRequest, AccountTransactionDto.class));
		accountFundService.purchaseFund(request.getAccountId(), request.getFundId(), request.getQuantity());
		FundTransaction fundTransaction = new FundTransaction();
		fundTransaction.setAccount(account);
		fundTransaction.setPerformedBy(performedBy);
		fundTransaction.setFund(fund);
		fundTransaction.setQuantity(request.getQuantity());
		fundTransaction.setTransactionType(FundTransactionType.PURCHASE);
		fundTransaction.setAmount(totalPrice);
		fundTransaction = fundTransactionRepository.save(fundTransaction);

		return convert(fundTransaction);
	}

	@Override
	public FundTransactionDto getById(Long fundTransactionId) {
		return convert(findById(fundTransactionId));

	}

	@Override
	public List<FundTransactionDto> getAll() {
		return fundTransactionRepository.findAll()
				.stream()
				.map(this::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<FundTransactionDto> getAllByAccountId(Long accountId) {
		return fundTransactionRepository.findAllByAccountId(accountId)
				.stream()
				.map(this::convert)
				.collect(Collectors.toList());
	}

	private FundTransaction findById(Long fundTransactionId) {
		return fundTransactionRepository.findById(fundTransactionId)
				.orElseThrow(
						() -> new BusinessException(HttpStatus.BAD_REQUEST,
						Arrays.asList("Fon işlemi bulunamadı: " + fundTransactionId)));
	}

	private FundTransactionDto convert(FundTransaction fundTransaction) {
		FundTransactionDto resultFundTransactionDto = fundTransactionConverter.convertToDto(fundTransaction);
		resultFundTransactionDto.setAccount(accountConverter.convertToDto(fundTransaction.getAccount()));

		Customer customer = fundTransaction.getAccount().getCustomer();
		customer.setAccounts(new ArrayList<Account>());
		resultFundTransactionDto.getAccount().setCustomer(customerConverter.convertToDto(customer));

		resultFundTransactionDto.setPerformedBy(employeeConverter.convertToDto(fundTransaction.getPerformedBy()));
		resultFundTransactionDto.setFund(fundConverter.convertToDto(fundTransaction.getFund()));
		return resultFundTransactionDto;
	}

	@Override
	public List<FundTransactionDto> getAllByCustomerId(Long customerId) {
		return fundTransactionRepository.findByAccount_CustomerId(customerId)
				.stream()
				.map(this::convert)
				.collect(Collectors.toList());
	}

}
