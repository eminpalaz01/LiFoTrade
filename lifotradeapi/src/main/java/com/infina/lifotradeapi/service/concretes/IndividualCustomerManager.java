package com.infina.lifotradeapi.service.concretes;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.infina.lifotradeapi.dto.AccountDto;
import com.infina.lifotradeapi.dto.IndividualCustomerDto;
import com.infina.lifotradeapi.dto.converter.AccountConverter;
import com.infina.lifotradeapi.dto.converter.IndividualCustomerConverter;
import com.infina.lifotradeapi.dto.request.IndividualCustomerSaveRequestDto;
import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.model.IndividualCustomer;
import com.infina.lifotradeapi.repository.IndividualCustomerRepository;
import com.infina.lifotradeapi.service.abstracts.AccountService;
import com.infina.lifotradeapi.service.abstracts.IndividualCustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndividualCustomerManager implements IndividualCustomerService {

	private final IndividualCustomerRepository individualCustomerRepository;
	private final AccountService accountService;
	private final IndividualCustomerConverter individualCustomerConverter;
	private final AccountConverter accountConverter;

	@Override
	@Transactional
	public IndividualCustomerDto save(IndividualCustomerDto individualCustomerDto) {
		if (existsByIdentificationNumber(individualCustomerDto.getIdentificationNumber())) {
			throw new BusinessException(
					HttpStatus.BAD_REQUEST, Arrays.asList("Aynı kimlik numaralı müşteri zaten kayıtlıdır."));
		}
		
		IndividualCustomer individualCustomerDb = individualCustomerRepository
				.save(individualCustomerConverter
				.convertToEntity(individualCustomerDto));
		
		IndividualCustomerDto individualCustomerDtoDb = individualCustomerConverter.convertToDto(individualCustomerDb);

		AccountDto accountDto = new AccountDto(null,
				individualCustomerDto.getName() + " " + individualCustomerDto.getSurname(), BigDecimal.ZERO,
				null, null, individualCustomerDtoDb, null, null);	
		
		AccountDto accountDtoDb = accountService.save(accountDto);

		individualCustomerDtoDb.getAccounts().add(accountDtoDb);

		return individualCustomerDtoDb;
	}

	@Override
	public IndividualCustomerDto getById(Long id) {
		 IndividualCustomer individualCustomer = findById(id);
		 
		List<AccountDto> accountsDto = individualCustomer.getAccounts().stream()
				.map(account -> {
			      AccountDto accountDto = accountConverter.convertToDto(account);
			      return accountDto;
		          }).collect(Collectors.toList());
		IndividualCustomerDto individualCustomerDto = individualCustomerConverter.convertToDto(individualCustomer);
		individualCustomerDto.setAccounts(accountsDto);
		
		return individualCustomerDto;
	}

	private IndividualCustomer findById(Long id) {
		return individualCustomerRepository.findById(id)
				.orElseThrow(
						() -> new BusinessException(
								HttpStatus.NOT_FOUND, Arrays.asList("Müşteri bulunamadı.")));
	}

	@Override
	public List<IndividualCustomerDto> getAll() {
		return findAll().stream()
				.map(individualCustomerConverter::convertToDto)
				.collect(Collectors.toList());
	}

	private List<IndividualCustomer> findAll() {
		return individualCustomerRepository.findAll();
	}

	@Override
	public Boolean existsById(Long id) {
		return individualCustomerRepository.existsById(id);
	}

	@Override
	public Boolean existsByIdentificationNumber(String identificationNumber) {
		return individualCustomerRepository.existsByIdentificationNumber(identificationNumber);
	}

	@Override
	public IndividualCustomerDto updateIndividualCustomer(Long customerId, IndividualCustomerSaveRequestDto updateRequest) {
	    IndividualCustomer individualCustomer = findById(customerId);
	    
	    individualCustomer.setAddress(updateRequest.getAddress());
	    individualCustomer.setIdentificationNumber(updateRequest.getIdentificationNumber());
	    individualCustomer.setName(updateRequest.getName());
	    individualCustomer.setSurname(updateRequest.getSurname());
	    individualCustomer.setGender(updateRequest.getGender());
	    individualCustomer.setDateOfBirth(updateRequest.getDateOfBirth());

	    IndividualCustomer updatedIndividualCustomer = individualCustomerRepository.save(individualCustomer);

	    return individualCustomerConverter.convertToDto(updatedIndividualCustomer);
	}

}
