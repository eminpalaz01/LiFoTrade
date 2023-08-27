package com.infina.lifotrade.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionSaveRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1192304361206541539L;

	Long accountId;
	
	String accountTransactionType;
	
	BigDecimal amount;
	
}
