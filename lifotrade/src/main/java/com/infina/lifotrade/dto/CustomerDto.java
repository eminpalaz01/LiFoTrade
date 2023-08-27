package com.infina.lifotrade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1403146985900222501L;

	private Long id;
	
	private List<AccountDto> accounts = new ArrayList<AccountDto>();

	private String address;

}
