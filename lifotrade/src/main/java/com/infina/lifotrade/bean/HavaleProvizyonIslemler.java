package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infina.lifotrade.dto.AccountTransactionDto;
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
public class HavaleProvizyonIslemler implements Serializable {

	private static final long serialVersionUID = -8865091335168982470L;

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;

	private List<AccountTransactionDto> accountTransactionsDto;

	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	private AccountTransactionDto accountTransactionDto;

	private Long searchAccountId;

	private Long searchCustomerId;

	public void searchCustomerId() {
		if (searchCustomerId != null) {
			Result result = lifotradeApi.get("/account-transaction/customer/" + searchCustomerId);
			if (result.isSuccess()) {
				accountTransactionsDto = convertToAccountTransactionsDto(result.getData());
				List<String> messages = new ArrayList<String>();
				messages.add("Müşterinin işlemleri getirildi.");
				messageBean.showSuccessMessage(messages);
			} else {
				List<String> errorList = MessagesUtility.convertToMessages(result.getData());
				messageBean.showFailureMessage(errorList);
			}
		} else {
			List<String> nullMessages = new ArrayList<String>();
			nullMessages.add("Müşteri seçilmedi.");
			messageBean.showFailureMessage(nullMessages);
		}
	}

	public void searchAccountId() {
		
		if (searchAccountId != null) {
			Result result = lifotradeApi.get("/account-transaction/account/" + searchAccountId);
			if (result.isSuccess()) {
				accountTransactionsDto = convertToAccountTransactionsDto(result.getData());
				List<String> messages = new ArrayList<String>();
				messages.add("Hesap işlemleri getirildi.");
				messageBean.showSuccessMessage(messages);
			} else {
				List<String> errorList = MessagesUtility.convertToMessages(result.getData());
				messageBean.showFailureMessage(errorList);
			}
		} else {
			List<String> nullMessages = new ArrayList<String>();
			nullMessages.add("Hesap seçilmedi.");
			messageBean.showFailureMessage(nullMessages);
		}
	}

	public void getAll() {
		Result result = lifotradeApi.get("/account-transaction");		
		if (result.isSuccess()) {
			accountTransactionsDto = convertToAccountTransactionsDto(result.getData());
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

	public List<AccountTransactionDto> convertToAccountTransactionsDto(String response) {
		List<AccountTransactionDto> accountTransactionListDto = null;
		try {
			accountTransactionListDto = objectMapper.readValue(response,
					new TypeReference<List<AccountTransactionDto>>() {
					});
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountTransactionListDto;
	}
}
