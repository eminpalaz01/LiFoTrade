package com.infina.lifotrade.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundPriceSaveRequestDto  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7125160178233481513L;

    private Long fundId;
	
    private BigDecimal price;
}
