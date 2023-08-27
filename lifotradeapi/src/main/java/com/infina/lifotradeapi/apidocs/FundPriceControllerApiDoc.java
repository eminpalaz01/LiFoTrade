package com.infina.lifotradeapi.apidocs;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.infina.lifotradeapi.dto.FundPriceDto;
import com.infina.lifotradeapi.dto.request.FundPriceSaveRequestDto;

public interface FundPriceControllerApiDoc {
	
	@PostMapping
    public ResponseEntity<FundPriceDto> save(@Valid @RequestBody FundPriceSaveRequestDto request);
    
    @GetMapping
    public ResponseEntity<FundPriceDto> getFundPriceById(Long id);

    @PostMapping("/save-all")
	ResponseEntity<String> saveAll(@Valid List<@Valid FundPriceSaveRequestDto> requestList, BindingResult bindingResult);
}
