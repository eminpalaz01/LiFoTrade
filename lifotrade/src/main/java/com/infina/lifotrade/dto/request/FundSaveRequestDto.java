package com.infina.lifotrade.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundSaveRequestDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3045899189757445662L;

	private String fundCode;

	private String isinCode;

	private String name;

}
