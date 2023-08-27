package com.infina.lifotradeapi.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundPriceSaveRequestDto {
	
	@NotNull(message = "Fon id boş olamaz")
    private Long fundId;
	
	@Positive(message = "Sıfırdan büyük bir değer girilmelidir")
    private BigDecimal price;
}
