package com.infina.lifotradeapi.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.infina.lifotradeapi.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateRequestDto implements Serializable {

	private static final long serialVersionUID = 3669750597691793125L;

	@NotBlank(message = "Ad alanı boş olamaz")
	private String name;

	@NotBlank(message = "Soyad alanı boş olamaz")
	private String surname;

	@NotBlank(message = "Kullanıcı adı alanı boş olamaz")
	private String username;

	@NotBlank(message = "Ünvan alanı boş olamaz")
	private String title;

	@NotBlank(message = "E-posta alanı boş olamaz")
	@Email(message = "Geçerli bir e-posta adresi giriniz")
	private String email;

	@NotNull(message = "Rol alanı boş olamaz")
	private Role role;
}
