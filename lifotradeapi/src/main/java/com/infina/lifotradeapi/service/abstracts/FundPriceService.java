package com.infina.lifotradeapi.service.abstracts;

import java.util.List;

import com.infina.lifotradeapi.dto.FundPriceDto;
import com.infina.lifotradeapi.dto.request.FundPriceSaveRequestDto;

public interface FundPriceService {
	
    FundPriceDto save(FundPriceSaveRequestDto fundPriceDto);
    
    FundPriceDto getById(Long fundPriceId);
    
	void saveAll(List<FundPriceSaveRequestDto> requestList);
}
