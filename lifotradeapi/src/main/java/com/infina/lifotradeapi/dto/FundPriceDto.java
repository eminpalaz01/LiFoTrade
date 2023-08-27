package com.infina.lifotradeapi.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundPriceDto {
	
	private Long id;

	private FundDto fund;

	private BigDecimal price;

	private Date priceChangingDate;
	
}
