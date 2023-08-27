package com.infina.lifotradeapi.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.infina.lifotradeapi.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto implements Serializable {

	private static final long serialVersionUID = -1986894820711265998L;

	@NotBlank(message = "Ad boş olamaz")
	private String name;

	@NotBlank(message = "Soyad boş olamaz")
	private String surname;

	@NotBlank(message = "Kullanıcı adı boş olamaz")
	private String username;

	@NotBlank(message = "Kullanıcı ünvanı boş olamaz")
	private String title;

	@Size(min = 8, max = 16, message = "Şifreler minimum 8 ve maksimum 16 karakterden oluşabilir.")
	private String password;

	@Email(message = "Email'iniz geçerli bir email değildir.")
	private String email;

	@NotNull(message = "Rol alanı doldurulmalıdır.")
	private Role role;
}
