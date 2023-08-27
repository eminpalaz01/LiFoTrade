package com.infina.lifotrade.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountFundIdRequest  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7804712193368653052L;

	private Long accountId;
	
	private Long fundId;
}
