package com.infina.lifotradeapi.dto.converter;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.infina.lifotradeapi.dto.FundDto;
import com.infina.lifotradeapi.model.Fund;

@Component
public class FundConverter {	
	
	public FundDto convertToDto(Fund fund) {	
        return new FundDto(fund.getId(),
                fund.getFundCode(),
                fund.getIsinCode(),
                fund.getName(),
                fund.getCurrentPrice(),
                fund.getCreationDate(),
                fund.getUpdateDate(),
                new ArrayList<>(),
                new ArrayList<>());
    }

    public Fund convertToEntity(FundDto fundDto) {
        return new Fund(fundDto.getId(),
                fundDto.getFundCode().trim(),
                fundDto.getIsinCode().trim(),
                fundDto.getName().trim(),
                fundDto.getCurrentPrice(),
                fundDto.getCreationDate(),
                fundDto.getUpdateDate(),
                new ArrayList<>(),
                new ArrayList<>());
    }
}
