package com.infina.lifotrade.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetMailRequestDto implements Serializable {

	private static final long serialVersionUID = 7073636345538732431L;
	@Email(message = "e-posta formatı hatalı")
	private String email;
}
