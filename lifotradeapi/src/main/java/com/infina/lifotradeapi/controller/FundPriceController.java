package com.infina.lifotradeapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infina.lifotradeapi.apidocs.FundPriceControllerApiDoc;
import com.infina.lifotradeapi.dto.FundPriceDto;
import com.infina.lifotradeapi.dto.request.FundPriceSaveRequestDto;
import com.infina.lifotradeapi.service.abstracts.FundPriceService;
import com.infina.lifotradeapi.util.BindingResultChecker;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/fund-price")
@RequiredArgsConstructor
public class FundPriceController implements FundPriceControllerApiDoc {

    private final FundPriceService fundPriceService;
	private final BindingResultChecker bindingResultChecker;

    @PostMapping
    @Override
    public ResponseEntity<FundPriceDto> save(@Valid @RequestBody FundPriceSaveRequestDto request) {
        return ResponseEntity.ok(fundPriceService.save(request));
    }
    
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<FundPriceDto> getFundPriceById(@PathVariable Long id) {
    	return ResponseEntity.ok(fundPriceService.getById(id));
    }
    
    @PostMapping("/save-all")
    @Override
    public ResponseEntity<String> saveAll(@Valid @RequestBody List<@Valid FundPriceSaveRequestDto> requestList,
    		BindingResult bindingResult) {
    	
		bindingResultChecker.checkBindingResult(bindingResult);
        fundPriceService.saveAll(requestList);
        return ResponseEntity.ok("Fon fiyatları kaydedildi.");
    }
}
