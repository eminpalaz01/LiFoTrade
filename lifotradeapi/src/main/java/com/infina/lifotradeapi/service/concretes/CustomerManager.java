package com.infina.lifotradeapi.service.concretes;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.infina.lifotradeapi.dto.CustomerDto;
import com.infina.lifotradeapi.dto.converter.CustomerConverter;
import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.model.Customer;
import com.infina.lifotradeapi.repository.CustomerRepository;
import com.infina.lifotradeapi.service.abstracts.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerManager implements CustomerService{
	
	private final CustomerRepository customerRepository;
	private final CustomerConverter customerConverter;

	@Override
	public CustomerDto getById(Long customerId) {
		return customerConverter.convertToDto(findById(customerId)) ;
	}

	private Customer findById(Long customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(
						() -> new BusinessException(HttpStatus.NOT_FOUND, Arrays.asList("Müşteri bulunamadı: " + customerId)));
	}
}
