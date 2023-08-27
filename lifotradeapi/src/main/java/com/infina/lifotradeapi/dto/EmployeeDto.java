package com.infina.lifotradeapi.dto;

import java.util.ArrayList;
import java.util.List;

import com.infina.lifotradeapi.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

	private Long id;

	private String name;

	private String surname;

	private String username;

	private String title;

	private String password;

	private String email;
	
	private Boolean isLoginBefore;

	private Role role;

	private List<FundTransactionDto> fundTransactions = new ArrayList<FundTransactionDto>();

	private List<AccountTransactionDto> accountransactions;
}
