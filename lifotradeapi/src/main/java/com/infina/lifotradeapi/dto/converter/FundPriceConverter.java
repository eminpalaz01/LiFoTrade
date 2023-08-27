package com.infina.lifotradeapi.dto.converter;

import org.springframework.stereotype.Component;

import com.infina.lifotradeapi.dto.FundPriceDto;
import com.infina.lifotradeapi.model.FundPrice;

@Component
public class FundPriceConverter {	
	
	public FundPriceDto convertToDto(FundPrice fundPrice) {
        return new FundPriceDto(fundPrice.getId(),
        		null,
                fundPrice.getPrice(),                
                fundPrice.getPriceChangingDate());
    }

    public FundPrice convertToEntity(FundPriceDto fundPriceDto) {
        return new FundPrice(fundPriceDto.getId(),
        		null,
                fundPriceDto.getPrice(),                
                fundPriceDto.getPriceChangingDate());
    }
}
