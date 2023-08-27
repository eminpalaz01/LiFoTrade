package com.infina.lifotradeapi.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import com.infina.lifotradeapi.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndividualCustomerSaveRequestDto {

	@NotBlank(message = "Adres boş olamaz.")
	private String address;

	@Pattern(regexp = "\\d{11}", message = "Kimlik numarası 11 karakterden ve sayılardan oluşmalıdır.")
	private String identificationNumber;

	@NotBlank(message = "Ad boş olamaz.")
	private String name;

	@NotBlank(message = "Soyad boş olamaz.")
	private String surname;

	@NotNull(message = "Cinsiyet alanı doldurulmalıdır.")
	private Gender gender;

	@PastOrPresent(message = "Doğum tarihi geçersizdir.")
	private LocalDate dateOfBirth;
	
}
