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
public class FundDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4514452990064835472L;

	private Long id;

	private String fundCode;

	private String isinCode;

	private String name;

	private BigDecimal currentPrice;

	private Date creationDate;

	private Date updateDate;

	private List<FundTransactionDto> fundTransactions = new ArrayList<FundTransactionDto>();

	private List<AccountFundDto> accountFundDtos = new ArrayList<AccountFundDto>();
}
