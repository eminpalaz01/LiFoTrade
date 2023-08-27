package com.infina.lifotrade.dto.request;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndividualCustomerSaveRequestDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7440201992692995804L;

	private String address;

	private String identificationNumber;

	private String name;

	private String surname;

	private String gender;

	private LocalDate dateOfBirth;
	
}
