package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infina.lifotrade.dto.request.AuthenticateRequestDto;
import com.infina.lifotrade.dto.request.PasswordResetMailRequestDto;
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
public class LoginBean implements Serializable{

	private static final long serialVersionUID = -8325366540094685803L;

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;
	
	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;
	
	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	private String username = "";
	private String password = "";
	private String receiverMail = "";
	private String forgotPasswordEmail = "";

	public String login() {
		AuthenticateRequestDto authenticateRequestDto = new AuthenticateRequestDto(username, password);
		
		Result result = lifotradeApi.post("/auth/login", authenticateRequestDto);
		if(result.isSuccess()) {
			List<String> messages = new ArrayList<String>();
			messages.add("Giriş işlemi başarılı");
			messageBean.showSuccessMessage(messages);
			LifotradeApi.setToken(result.getData());
			return "home?faces-redirect=true";
		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
			return null;
		}
	}

	public void forgotPassword() {
		PasswordResetMailRequestDto requestDto = new PasswordResetMailRequestDto(forgotPasswordEmail);
		Result result = lifotradeApi.post("/employee/reset-password-mail",requestDto );
		LifotradeApi.setToken("");
		if(result.isSuccess()) {
			List<String> messages = new ArrayList<String>();
			messages.add("Şifre sıfırlama başarılı");
			messageBean.showSuccessMessage(messages);			
		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
		}
	}
	
	public String showGreeting() {
		return "";
	}
}
