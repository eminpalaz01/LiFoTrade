package com.infina.lifotrade.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountFundDto implements Serializable {
	
	private static final long serialVersionUID = -7834392598171234795L;

	private AccountDto accountDto;
	
	private FundDto fundDto;
	
	private Long quantity;
}
