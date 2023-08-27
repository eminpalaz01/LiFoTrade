package com.infina.lifotradeapi.service.abstracts;

import java.util.List;

import com.infina.lifotradeapi.dto.FundDto;
import com.infina.lifotradeapi.dto.request.FundSaveRequestDto;
import com.infina.lifotradeapi.model.Fund;

public interface FundService {

	FundDto save(FundSaveRequestDto request);

	FundDto getById(Long fundId);

	List<FundDto> getAll();

	Fund findById(Long fundId);

	Fund save(Fund fund);

	FundDto updateFund(Long fundId, FundSaveRequestDto updateRequest);
}
