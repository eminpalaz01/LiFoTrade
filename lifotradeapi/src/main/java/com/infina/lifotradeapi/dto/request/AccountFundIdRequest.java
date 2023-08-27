package com.infina.lifotradeapi.dto.request;

import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountFundIdRequest {
	
	@Positive(message = "Hesap id değeri 0 dan büyük olmalıdır.")
	private Long accountId;
	
	@Positive(message = "Fon id değeri 0 dan büyük olmalıdır.")
	private Long fundId;
}
