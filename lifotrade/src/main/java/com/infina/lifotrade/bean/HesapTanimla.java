package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infina.lifotrade.dto.AccountDto;
import com.infina.lifotrade.dto.request.AccountSaveRequest;
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
public class HesapTanimla implements Serializable{
	
	private static final long serialVersionUID = 2011125272864072624L;

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;
	
	private String name;

	private Long customerId;
	
	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
	
	public void accountSaveRequest() {
		AccountSaveRequest request = new AccountSaveRequest(name,customerId);
		Result result = lifotradeApi.post("/account", request);
		if(result.isSuccess()) {
			AccountDto accountDto = convertToAccountDto(result);
			List<String> messages = new ArrayList<String>();
			messages.add("Hesap ekleme başarılı No: " + accountDto.getId());
			messageBean.showSuccessMessage(messages);
		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
		}
	}
	

	public AccountDto convertToAccountDto(Result result) {
		AccountDto accountDto = null;
		try {
			accountDto = objectMapper.readValue(result.getData(), AccountDto.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountDto;
	}
	
}
