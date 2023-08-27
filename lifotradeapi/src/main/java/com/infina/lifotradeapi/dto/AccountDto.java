package com.infina.lifotradeapi.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
	
	private Long id;
	
	private String name;

	private BigDecimal balance;

	private Date creationDate;

	private Date updateDate;

	private CustomerDto customer;

	private List<FundTransactionDto> fundTransactions = new ArrayList<FundTransactionDto>();
	
	private List<AccountFundDto> accountFundDtos = new ArrayList<AccountFundDto>();

}
