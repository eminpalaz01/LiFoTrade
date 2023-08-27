package com.infina.lifotrade.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infina.lifotrade.dto.EmployeeDto;
import com.infina.lifotrade.services.common.LifotradeApi;
import com.infina.lifotrade.services.common.ObjectMapperSingleton;
import com.infina.lifotrade.services.common.Result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ManagedBean
@ViewScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1434668364624870473L;

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	private EmployeeDto employeeDto = new EmployeeDto();

	private Boolean isLoginBefore;
	

	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	public boolean isLoggedIn() {
		Result result = lifotradeApi.get("/employee/me");

		if (result.isSuccess()) {
			EmployeeDto newEmployeeDto = convertToEmployeeDto(result);			
			employeeDto = newEmployeeDto;
			return true;
		} else {
			if (result.getStatusCode() == 423) {
				Result resultFirstLogin = lifotradeApi.get("/employee/me");
				if (resultFirstLogin.isSuccess()) {
					employeeDto = convertToEmployeeDto(resultFirstLogin);
					isLoginBefore = Boolean.FALSE;
				}
				goToSifreDegistirPage();
			}
			return false;
		}

	}

	public void goToSifreDegistirPage() {
		ExternalContext externalContext = getExternalContext();
		redirectUrl(externalContext, "sifredegistir.xhtml");
	}

	private void redirectUrl(ExternalContext externalContext, String url) {
		try {
			externalContext.redirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	private EmployeeDto convertToEmployeeDto(Result result) {
		try {
			return objectMapper.readValue(result.getData(), EmployeeDto.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employeeDto;
	}
}
