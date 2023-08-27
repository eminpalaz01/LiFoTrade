package com.infina.lifotrade.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FundTransactionSaveRequestDto  implements Serializable {
    
	private static final long serialVersionUID = -7012685320189812362L;

    private Long accountId;
    
    private Long fundId;
    
    private Long quantity;
    
    private String transactionType;
}
