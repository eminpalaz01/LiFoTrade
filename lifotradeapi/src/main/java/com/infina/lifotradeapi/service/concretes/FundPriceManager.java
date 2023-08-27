package com.infina.lifotradeapi.service.concretes;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.infina.lifotradeapi.dto.FundPriceDto;
import com.infina.lifotradeapi.dto.converter.FundPriceConverter;
import com.infina.lifotradeapi.dto.request.FundPriceSaveRequestDto;
import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.model.Fund;
import com.infina.lifotradeapi.model.FundPrice;
import com.infina.lifotradeapi.repository.FundPriceRepository;
import com.infina.lifotradeapi.service.abstracts.FundPriceService;
import com.infina.lifotradeapi.service.abstracts.FundService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FundPriceManager implements FundPriceService {
    
    private final FundPriceRepository fundPriceRepository;
    private final FundPriceConverter converter;
    private final ModelMapper modelMapper;
    private final FundService fundService;

    @Override
    public FundPriceDto save(FundPriceSaveRequestDto fundPriceDto) {
        FundPrice fundPrice = modelMapper.map(fundPriceDto,FundPrice.class);
        fundPrice = fundPriceRepository.save(fundPrice);
        updateCurrentPriceInAssociatedFund(fundPrice);
        return converter.convertToDto(fundPrice);
    }

    @Override
    public FundPriceDto getById(Long fundPriceId) {
        FundPrice fundPrice = fundPriceRepository.findById(fundPriceId)
                .orElseThrow(
                		() -> new BusinessException(
                				HttpStatus.BAD_REQUEST, Arrays.asList("Fon bulunamadı " + fundPriceId)));
        return converter.convertToDto(fundPrice);
    }
    
    private void updateCurrentPriceInAssociatedFund(FundPrice fundPrice) {
        Fund fund = fundPrice.getFund();
        fund.setCurrentPrice(fundPrice.getPrice());
        fundService.save(fund);
    }

	@Override
	@Transactional
	public void saveAll(List<FundPriceSaveRequestDto> requestList) {
		List<FundPrice> fundPrices = requestList.stream()
	            .map(requestDto -> {
	                Fund fund = fundService.findById(requestDto.getFundId());
	                BigDecimal price = requestDto.getPrice();

	                FundPrice fundPrice = new FundPrice();
	                fundPrice.setFund(fund);
	                fundPrice.setPrice(price);
	                updateCurrentPriceInAssociatedFund(fundPrice);
	                return fundPrice;
	            })
	            .collect(Collectors.toList());

	    fundPriceRepository.saveAll(fundPrices);
	}
}
