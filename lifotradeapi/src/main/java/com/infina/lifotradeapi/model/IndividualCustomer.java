package com.infina.lifotradeapi.model;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.infina.lifotradeapi.enums.Gender;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "individual_customer")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class IndividualCustomer extends Customer {

	@Column(name = "identification_number", length = 11, unique = true)
	private String identificationNumber;

	@Column(name = "name", length = 20)
	private String name;

	@Column(name = "surname", length = 20)
	private String surname;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "gender")
	private Gender gender;
	
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;
	
	public IndividualCustomer() {
		super();
	}

	public IndividualCustomer(Long id, List<Account> accounts, String address,
			String identificationNumber, String name, String surname, Gender gender, LocalDate dateOfBirth) {
		super(id, accounts, address);
		this.identificationNumber = identificationNumber;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
	}
	
	
}
