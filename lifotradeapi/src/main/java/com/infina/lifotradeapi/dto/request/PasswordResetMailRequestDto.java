package com.infina.lifotradeapi.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetMailRequestDto implements Serializable {
	private static final long serialVersionUID = 2271455026372502940L;

	@Email(message = "Geçerli bir email giriniz.")
	private String email;
}
