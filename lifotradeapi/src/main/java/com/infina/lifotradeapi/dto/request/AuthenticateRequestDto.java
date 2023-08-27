package com.infina.lifotradeapi.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateRequestDto implements Serializable {

	private static final long serialVersionUID = 8959350587773545739L;
	
	@NotBlank(message = "Kullancı adı boş olamaz.")
	private String username;

	@NotBlank(message = "Şifre boş olamaz.")
	private String password;	

}
