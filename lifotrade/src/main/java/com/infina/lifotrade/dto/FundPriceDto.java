package com.infina.lifotrade.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundPriceDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6126391130344086979L;

	private Long id;

	private FundDto fund;

	private BigDecimal price;

	private Date priceChangingDate;
	
}
