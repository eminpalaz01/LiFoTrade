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
public class EmployeeDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8978235837504197381L;

	private Long id;

	private String name;

	private String surname;

	private String username;

	private String title;

	private String password;

	private String email;
	
	private Boolean isLoginBefore;

	private String role;

	private List<FundTransactionDto> fundTransactions = new ArrayList<FundTransactionDto>();

	private List<AccountTransactionDto> accountransactions;
}
