package com.infina.lifotradeapi.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
	
	private Long id;
	
	private List<AccountDto> accounts = new ArrayList<AccountDto>();

	private String address;

}
