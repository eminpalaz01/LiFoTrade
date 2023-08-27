package com.infina.lifotradeapi.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.infina.lifotradeapi.enums.AccountTransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionDto {

	private Long id;

	private BigDecimal amount;

	private EmployeeDto performedBy;

	private AccountDto account;

	private AccountTransactionType accountTransactionType;

	private Date transactionDate;	
}
