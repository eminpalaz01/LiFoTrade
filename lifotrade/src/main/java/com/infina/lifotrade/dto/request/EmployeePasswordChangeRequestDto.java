package com.infina.lifotrade.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePasswordChangeRequestDto implements Serializable {

	private static final long serialVersionUID = -4435602103194687634L;

	private String oldPassword;

	private String newPassword;

	private String confirmNewPassword;
}
