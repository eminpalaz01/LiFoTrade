package com.infina.lifotradeapi.dto.request;

import javax.validation.constraints.NotNull;

import com.infina.lifotradeapi.enums.FundTransactionType;

import lombok.Data;

@Data
public class FundTransactionSaveRequestDto {
    
    @NotNull(message = "Hesap id boş olamaz")
    private Long accountId;
    
    @NotNull(message = "Fon boş olamaz")
    private Long fundId;
    
    @NotNull(message = "Adet değeri boş olamaz")
    private Long quantity;
    
    @NotNull(message = "İşlem türü boş olamaz")
    private FundTransactionType transactionType;
}
