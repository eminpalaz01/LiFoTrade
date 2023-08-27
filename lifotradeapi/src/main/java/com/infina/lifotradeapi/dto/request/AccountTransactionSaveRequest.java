package com.infina.lifotradeapi.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.infina.lifotradeapi.enums.AccountTransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionSaveRequest implements Serializable{
	
	private static final long serialVersionUID = -1192304361206541539L;

	@Positive(message = "Hesap id değeri 0 dan büyük olmalıdır.")
	Long accountId;
	
	@NotNull(message = "İşlem tipi alanı doldurulmalıdır.")
	AccountTransactionType accountTransactionType;
	
	@Positive(message = "miktar 0 dan büyük olmalıdır.")
	BigDecimal amount;
	
}
