package com.infina.lifotradeapi.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePasswordChangeRequestDto implements Serializable {

	private static final long serialVersionUID = -4435602103194687634L;

	@NotBlank(message = "Eski şifre alanı boş olamaz")
	private String oldPassword;

	@NotBlank(message = "Yeni şifre alanı boş olamaz")
	private String newPassword;

	@NotBlank(message = "Yeni şifre tekrarı alanı boş olamaz")
	private String confirmNewPassword;
}
