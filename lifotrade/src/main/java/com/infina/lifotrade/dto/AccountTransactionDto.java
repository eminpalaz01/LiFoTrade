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
public class AccountTransactionDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2675938669716892080L;

	private Long id;

	private BigDecimal amount;

	private EmployeeDto performedBy;

	private AccountDto account;

	private String accountTransactionType;

	private Date transactionDate;	
}
