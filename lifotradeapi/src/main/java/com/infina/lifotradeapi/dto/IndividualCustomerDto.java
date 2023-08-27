package com.infina.lifotradeapi.dto;

import java.time.LocalDate;
import java.util.List;

import com.infina.lifotradeapi.enums.Gender;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class IndividualCustomerDto extends CustomerDto {

	private String identificationNumber;

	private String name;

	private String surname;

	private Gender gender;
	
	private LocalDate dateOfBirth;
	
	public IndividualCustomerDto(Long id, List<AccountDto> accounts, String address,
			String identificationNumber, String name, String surname, Gender gender, LocalDate dateOfBirth) {
		super(id, accounts, address);
		this.identificationNumber = identificationNumber;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
	}

}
