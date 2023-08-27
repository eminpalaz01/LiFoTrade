package com.infina.lifotrade.services.common;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  Result  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4139074013588531930L;
	private Boolean success;
	private String data;
	private int statusCode;
	
	public Boolean isSuccess() {
		return success;
	}

}
