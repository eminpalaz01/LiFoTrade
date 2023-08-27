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
public class FundTransactionDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5279761870584147381L;

	private Long id;

	private BigDecimal amount;

	private AccountDto account;

	private EmployeeDto performedBy;

	private String transactionType;

	private Date transactionDate;

	private FundDto fund;
	
	private Long quantity;
	
}
