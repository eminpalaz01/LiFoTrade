package com.infina.lifotrade.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountPutNameRequest implements Serializable {

	private static final long serialVersionUID = 5806812672707395252L;
	
	@NotBlank(message = "Hesap adı boş olamaz.")
	private String name;
}
