package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infina.lifotrade.dto.AccountDto;
import com.infina.lifotrade.dto.AccountTransactionDto;
import com.infina.lifotrade.dto.request.AccountTransactionSaveRequest;
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
public class HavaleProvizyonTanimla implements Serializable{
	
	private static final long serialVersionUID = 7889430909402547088L;

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;
	
	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	private Long accountNo;
	
	private BigDecimal balance= BigDecimal.ZERO;
	private BigDecimal amount;
	private LocalDate date = LocalDate.now();
	
	private String type;
	
	public void getAccountAndSetBalance() {
		Result result = lifotradeApi.get("/account/"+ accountNo);
		if(result.isSuccess()) {
			List<String> messages = new ArrayList<String>();
			messages.add("Hesap getirme başarılı");
			messageBean.showSuccessMessage(messages);
			AccountDto accountDto = convertToAccountDto(result);
			balance = accountDto.getBalance();
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
	
	public void doTransaction() {
		AccountTransactionSaveRequest accountTransactionSaveRequest = new AccountTransactionSaveRequest(accountNo, type, amount);
		Result result = lifotradeApi.post("/account-transaction", accountTransactionSaveRequest);
		if(result.isSuccess()) {
			List<String> messages = new ArrayList<String>();
			messages.add("İşlem başarılı");
			messageBean.showSuccessMessage(messages);
			AccountTransactionDto accountTransactionDto = convertToAccountTransactionDto(result);
			balance = accountTransactionDto.getAccount().getBalance();
		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
		}
	}
	
	public AccountTransactionDto convertToAccountTransactionDto(Result result) {
		AccountTransactionDto accountTransactionDto = null;
		try {
			accountTransactionDto = objectMapper.readValue(result.getData(), AccountTransactionDto.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountTransactionDto;
	}
}
