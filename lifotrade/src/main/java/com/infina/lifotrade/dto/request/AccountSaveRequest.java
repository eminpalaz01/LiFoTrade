package com.infina.lifotrade.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSaveRequest implements Serializable {

	private static final long serialVersionUID = -7215288180158784545L;

	private String name;

	private Long customerId;

}
