package com.infina.lifotradeapi.service.abstracts;

import java.util.List;

import com.infina.lifotradeapi.dto.FundTransactionDto;
import com.infina.lifotradeapi.dto.request.FundTransactionSaveRequestDto;

public interface FundTransactionService {
	
    FundTransactionDto save(FundTransactionSaveRequestDto request);
    
    FundTransactionDto getById(Long fundTransactionId);
    
    List<FundTransactionDto> getAll();
    
	List<FundTransactionDto> getAllByAccountId(Long accountId);
	
	List<FundTransactionDto> getAllByCustomerId(Long customerId);
}
