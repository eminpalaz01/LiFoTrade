package com.infina.lifotradeapi.dto.converter;

import org.springframework.stereotype.Component;

import com.infina.lifotradeapi.dto.FundTransactionDto;
import com.infina.lifotradeapi.model.FundTransaction;

@Component
public class FundTransactionConverter {
    
    public FundTransactionDto convertToDto(FundTransaction fundTransaction) {
    	
        return new FundTransactionDto(fundTransaction.getId(),
                fundTransaction.getAmount(),
                null,
                null,
                fundTransaction.getTransactionType(),
                fundTransaction.getTransactionDate(),
                null,
                fundTransaction.getQuantity());
    }

    public FundTransaction convertToEntity(FundTransactionDto fundTransactionDto) {
        return new FundTransaction(fundTransactionDto.getId(),
                fundTransactionDto.getAmount(),
                null,
                null,
                fundTransactionDto.getTransactionType(),
                fundTransactionDto.getTransactionDate(),
                null,
                fundTransactionDto.getQuantity());
    }
}
