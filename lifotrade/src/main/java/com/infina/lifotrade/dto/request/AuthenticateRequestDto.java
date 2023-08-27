package com.infina.lifotrade.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateRequestDto implements Serializable {

	/**
	 * Default
	 */
	private static final long serialVersionUID = 8959350587773545739L;
	
	private String username;

	private String password;	

}
