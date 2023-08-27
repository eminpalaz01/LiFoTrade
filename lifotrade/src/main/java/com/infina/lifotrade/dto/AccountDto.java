package com.infina.lifotrade.dto;

import java.io.Serializable;
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
public class AccountDto  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2536040051407542751L;

	private Long id;
	
	private String name;

	private BigDecimal balance;

	private Date creationDate;

	private Date updateDate;

	private CustomerDto customer;

	private List<FundTransactionDto> fundTransactions = new ArrayList<FundTransactionDto>();
	
	private List<AccountFundDto> accountFundDtos = new ArrayList<AccountFundDto>();

}
