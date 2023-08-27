package com.infina.lifotrade.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualCustomerDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3064595223823011952L;

	private Long id;

	private String identificationNumber;

	private String name;

	private String surname;

	private String gender;

	private LocalDate dateOfBirth;

	private List<AccountDto> accounts = new ArrayList<AccountDto>();

	private String address;

}
