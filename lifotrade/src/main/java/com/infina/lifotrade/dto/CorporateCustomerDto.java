package com.infina.lifotrade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorporateCustomerDto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7634905670420882416L;

	private Long id;
	
	private List<AccountDto> accounts = new ArrayList<AccountDto>();

	private String address;

	private String taxNumber;

	private String title;

}
