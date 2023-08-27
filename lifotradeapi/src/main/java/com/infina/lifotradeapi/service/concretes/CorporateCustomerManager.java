package com.infina.lifotradeapi.service.concretes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.infina.lifotradeapi.dto.AccountDto;
import com.infina.lifotradeapi.dto.CorporateCustomerDto;
import com.infina.lifotradeapi.dto.converter.AccountConverter;
import com.infina.lifotradeapi.dto.converter.CorporateCustomerConverter;
import com.infina.lifotradeapi.dto.request.CorporateCustomerSaveRequestDto;
import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.model.CorporateCustomer;
import com.infina.lifotradeapi.repository.CorporateCustomerRepository;
import com.infina.lifotradeapi.service.abstracts.AccountService;
import com.infina.lifotradeapi.service.abstracts.CorporateCustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorporateCustomerManager implements CorporateCustomerService {

	private final CorporateCustomerRepository corporateCustomerRepository;
	private final AccountService accountService;
	private final CorporateCustomerConverter corporateCustomerConverter;
	private final AccountConverter accountConverter;

	@Override
	@Transactional
	public CorporateCustomerDto save(CorporateCustomerDto corporateCustomerDto) {

		if (existsByTaxNumber(corporateCustomerDto.getTaxNumber())) {
			List<String> messages = new ArrayList<String>();
			messages.add("Vergi numarası zaten kayıtlıdır.");
			throw new BusinessException(HttpStatus.BAD_REQUEST, messages);
		}

		CorporateCustomer corporateCustomerDb = corporateCustomerRepository
				.save(corporateCustomerConverter.convertToEntity(corporateCustomerDto));

		CorporateCustomerDto corporateCustomerDtoDb = corporateCustomerConverter.convertToDto(corporateCustomerDb);

		AccountDto accountDto = new AccountDto(null, corporateCustomerDto.getTitle(), BigDecimal.ZERO, null, null,
				corporateCustomerDtoDb, null, null);
        
		AccountDto accountDtoDb = accountService.save(accountDto);

		corporateCustomerDtoDb.getAccounts().add(accountDtoDb);

		return corporateCustomerDtoDb;
	}

	@Override
	public CorporateCustomerDto getById(Long id) {
		CorporateCustomer corporateCustomer = findById(id);
				
		List<AccountDto> accountsDto = corporateCustomer.getAccounts().stream()
				.map(account -> {
			      AccountDto accountDto = accountConverter.convertToDto(account);
			      return accountDto;
		          }).collect(Collectors.toList());
		CorporateCustomerDto corporateCustomerDto = corporateCustomerConverter.convertToDto(corporateCustomer);
		corporateCustomerDto.setAccounts(accountsDto);
		return corporateCustomerDto;

	}

	private CorporateCustomer findById(Long id) {
		return corporateCustomerRepository.findById(id)
				.orElseThrow(
						() -> new BusinessException(
								HttpStatus.NOT_FOUND, Arrays.asList("Müşteri bulunamadı.")));
	}

	@Override
	public List<CorporateCustomerDto> getAll() {
		return findAll().stream()
			.map(corporateCustomer -> {			
			  return corporateCustomerConverter.convertToDto(corporateCustomer);
		}).collect(Collectors.toList());
	}

	private List<CorporateCustomer> findAll() {
		return corporateCustomerRepository.findAll();
	}

	@Override
	public Boolean existsById(Long id) {
		return corporateCustomerRepository.existsById(id);
	}

	@Override
	public Boolean existsByTaxNumber(String taxNumber) {
		return corporateCustomerRepository.existsByTaxNumber(taxNumber);
	}

	@Override
	public CorporateCustomerDto updateCorporateCustomer(Long customerId,
	        CorporateCustomerSaveRequestDto updateRequest) {
	    CorporateCustomer corporateCustomer = findById(customerId);

	    corporateCustomer.setTaxNumber(updateRequest.getTaxNumber());
	    corporateCustomer.setTitle(updateRequest.getTitle());
	    corporateCustomer.setAddress(updateRequest.getAddress());

	    CorporateCustomer updatedCorporateCustomer = corporateCustomerRepository.save(corporateCustomer);

	    return corporateCustomerConverter.convertToDto(updatedCorporateCustomer);
	}

}
