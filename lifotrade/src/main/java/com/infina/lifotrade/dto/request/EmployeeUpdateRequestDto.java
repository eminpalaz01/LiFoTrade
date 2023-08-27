package com.infina.lifotrade.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateRequestDto implements Serializable {

	private static final long serialVersionUID = 3669750597691793125L;

	private String name;

	private String surname;

	private String username;

	private String title;

	private String email;

	private String role;
}
