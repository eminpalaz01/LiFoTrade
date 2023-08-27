package com.infina.lifotradeapi.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundSaveRequestDto {

	@NotBlank
	@Size(min = 3, max = 3, message = "Fon kodu 3 karakter içermelidir")
	private String fundCode;

	@NotBlank
	@Size(min = 12, max = 12, message = "ISIN kodu 12 karakter içermelidir")
	private String isinCode;

	@NotBlank(message = "Fon adı girilmelidir")
	private String name;

}
