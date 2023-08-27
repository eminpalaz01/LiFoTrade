package com.infina.lifotradeapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infina.lifotradeapi.apidocs.FundControllerApiDoc;
import com.infina.lifotradeapi.dto.FundDto;
import com.infina.lifotradeapi.dto.request.FundSaveRequestDto;
import com.infina.lifotradeapi.service.abstracts.FundService;
import com.infina.lifotradeapi.util.BindingResultChecker;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/fund")
@RequiredArgsConstructor
public class FundController implements FundControllerApiDoc {

	private final FundService fundService;
	private final BindingResultChecker bindingResultChecker;

	@Override
	@PostMapping
	public ResponseEntity<FundDto> save(@Valid @RequestBody FundSaveRequestDto request, BindingResult bindingResult) {
		bindingResultChecker.checkBindingResult(bindingResult);

		return ResponseEntity.ok(fundService.save(request));
	}

	@Override
	@GetMapping("/{fundId}")
	public ResponseEntity<FundDto> getFundById(Long fundId) {
		return ResponseEntity.ok(fundService.getById(fundId));
	}

	@Override
	@GetMapping
	public ResponseEntity<List<FundDto>> getAllFunds() {
		return ResponseEntity.ok(fundService.getAll());
	}

	@Override
	@PutMapping("/{fundId}")
	public ResponseEntity<FundDto> updateFund(@PathVariable Long fundId,
			@Valid @RequestBody FundSaveRequestDto updateRequest, BindingResult bindingResult) {

		bindingResultChecker.checkBindingResult(bindingResult);
		return ResponseEntity.ok(fundService.updateFund(fundId, updateRequest));
	}
}
