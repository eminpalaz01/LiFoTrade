package com.infina.lifotrade.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorporateCustomerSaveRequestDto  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2327325862963145598L;

	private String taxNumber;

	private String title;
	
	private String address;

}
