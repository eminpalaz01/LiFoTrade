package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infina.lifotrade.dto.request.EmployeePasswordChangeRequestDto;
import com.infina.lifotrade.services.common.LifotradeApi;
import com.infina.lifotrade.services.common.MessagesUtility;
import com.infina.lifotrade.services.common.ObjectMapperSingleton;
import com.infina.lifotrade.services.common.Result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ViewScoped
@ManagedBean
public class SifreDegistir implements Serializable {

	private static final long serialVersionUID = 8648221721623569172L;
	
	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	
	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;
	
	private String oldPassword = "";

	private String newPassword = "";

	private String confirmNewPassword = "";

	private EmployeePasswordChangeRequestDto employeePasswordChangeRequestDto;

	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	public void passwordChange() {
		employeePasswordChangeRequestDto = new EmployeePasswordChangeRequestDto(oldPassword, newPassword, confirmNewPassword);
		Result result = lifotradeApi.post("/employee/change-password", employeePasswordChangeRequestDto);
		if (result.isSuccess()) {
			List<String> messages = new ArrayList<String>();
			messages.add("Şifre Değiştirme işlemi başarılı.");
			messageBean.showSuccessMessage(messages);
		} else {
			
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
		}
	}
}
