package com.infina.lifotradeapi.service.concretes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.infina.lifotradeapi.dto.AccountDto;
import com.infina.lifotradeapi.dto.EmployeeDto;
import com.infina.lifotradeapi.dto.FundDto;
import com.infina.lifotradeapi.dto.FundTransactionDto;
import com.infina.lifotradeapi.dto.converter.AccountConverter;
import com.infina.lifotradeapi.dto.converter.EmployeeConverter;
import com.infina.lifotradeapi.dto.converter.FundConverter;
import com.infina.lifotradeapi.dto.converter.FundTransactionConverter;
import com.infina.lifotradeapi.dto.request.FundSaveRequestDto;
import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.model.Fund;
import com.infina.lifotradeapi.repository.FundRepository;
import com.infina.lifotradeapi.service.abstracts.FundService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FundManager implements FundService {

	private final FundRepository fundRepository;
	private final FundConverter converter;
	private final ModelMapper modelMapper;
	private final FundTransactionConverter fundTransactionConverter;
	private final AccountConverter accountConverter;
	private final EmployeeConverter employeeConverter;

	@Override
	public Fund findById(Long fundId) {
		return fundRepository.findById(fundId).orElseThrow(
				() -> new BusinessException(
						HttpStatus.BAD_REQUEST, Arrays.asList("Fon bulunamadı: " + fundId)));
	}

	@Override
	public FundDto save(FundSaveRequestDto fundDto) {
		validateUniqueFundCode(fundDto.getFundCode());
		validateUniqueIsinCode(fundDto.getIsinCode());
		return converter.convertToDto(save(modelMapper.map(fundDto, Fund.class)));
	}


	
	@Override
	public FundDto getById(Long fundId) {
		Fund fund = findById(fundId);
		FundDto fundDto = converter.convertToDto(fund);
		List<FundTransactionDto> fundTransactionDtos = fund.getFundTransactions()
				.stream()
				.map(fundTransaction -> {
				AccountDto accountDto = accountConverter.convertToDto(fundTransaction.getAccount());
				EmployeeDto employeeDto = employeeConverter.convertToDto(fundTransaction.getPerformedBy());
				FundTransactionDto fundTransactionDto = fundTransactionConverter.convertToDto(fundTransaction);
				fundTransactionDto.setAccount(accountDto);
				fundTransactionDto.setPerformedBy(employeeDto);
				return fundTransactionDto;
			}).collect(Collectors.toList());
		fundDto.setFundTransactions(fundTransactionDtos);
		return fundDto;
	}

	@Override
	public List<FundDto> getAll() {
		return fundRepository.findAll().stream().map(converter::convertToDto).collect(Collectors.toList());
	}

	@Override
	public Fund save(Fund fund) {
		return fundRepository.save(fund);

	}

	@Override
	public FundDto updateFund(Long fundId, FundSaveRequestDto updateRequest) {
		validateUniqueFundCode(updateRequest.getFundCode());
		validateUniqueIsinCode(updateRequest.getIsinCode());
		Fund fundToUpdate = findById(fundId);
		fundToUpdate.setFundCode(updateRequest.getFundCode());
		fundToUpdate.setIsinCode(updateRequest.getIsinCode());
		fundToUpdate.setName(updateRequest.getName());

		Fund updatedFund = save(fundToUpdate);

		return converter.convertToDto(updatedFund);
	}
	
	private void validateUniqueFundCode(String fundCode) {
	    if (existsByFundCode(fundCode)) {
	        throw new BusinessException(HttpStatus.BAD_REQUEST, Arrays.asList("Bu fon kayıtlıdır"));
	    }
	}

	private void validateUniqueIsinCode(String isinCode) {
	    if (existsByIsinCode(isinCode)) {
	        throw new BusinessException(HttpStatus.BAD_REQUEST, Arrays.asList("Bu Isin kodu kayıtlıdır."));
	    }
	}
	
	private Boolean existsByIsinCode(String isinCode) {
	    return fundRepository.existsByIsinCode(isinCode);
	}

	private Boolean existsByFundCode(String fundCode) {
	    return fundRepository.existsByFundCode(fundCode);
	}
}
