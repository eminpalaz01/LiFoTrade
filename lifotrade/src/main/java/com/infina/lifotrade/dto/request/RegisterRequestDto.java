package com.infina.lifotrade.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1986894820711265998L;

	private String name;

	private String surname;

	private String username;
	
	private String title;

	private String password;

	private String email;

	private String role;
	
	
}
