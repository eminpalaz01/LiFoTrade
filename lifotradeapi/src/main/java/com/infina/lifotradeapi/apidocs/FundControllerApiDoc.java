package com.infina.lifotradeapi.apidocs;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.infina.lifotradeapi.dto.FundDto;
import com.infina.lifotradeapi.dto.request.FundSaveRequestDto;

public interface FundControllerApiDoc {

	public ResponseEntity<FundDto> save(@RequestBody FundSaveRequestDto request, BindingResult bindingResult);

	public ResponseEntity<FundDto> getFundById(@PathVariable Long fundId);

	public ResponseEntity<List<FundDto>> getAllFunds();

	ResponseEntity<FundDto> updateFund(Long fundId, @Valid FundSaveRequestDto updateRequest,
			BindingResult bindingResult);
}
