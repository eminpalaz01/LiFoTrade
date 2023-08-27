package com.infina.lifotradeapi.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.infina.lifotradeapi.enums.FundTransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundTransactionDto {

	private Long id;

	private BigDecimal amount;

	private AccountDto account;

	private EmployeeDto performedBy;

	private FundTransactionType transactionType;

	private Date transactionDate;

	private FundDto fund;
	
	private Long quantity;
	
}
