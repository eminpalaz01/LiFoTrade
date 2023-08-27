package com.infina.lifotradeapi.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSaveRequest implements Serializable {

	private static final long serialVersionUID = -7215288180158784545L;

	@NotBlank(message = "Hesap adı boş olamaz.")
	private String name;

	@NotNull(message = "Müşteri numarası boş olamaz.")
	private Long customerId;

}
