package com.infina.lifotradeapi.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorporateCustomerSaveRequestDto {

	@Pattern(regexp = "\\d{10}", message = "Vergi numarası 10 karakterden ve sayılardan oluşmalıdır.")
	private String taxNumber;

	@NotBlank(message = "Kurum ünvanı boş olamaz.")
	private String title;
	
	@NotBlank(message = "Adres boş olamaz.")
	@Size(max = 255, message = "En fazla 255 karakter girilmelidir.")
	private String address;

}
