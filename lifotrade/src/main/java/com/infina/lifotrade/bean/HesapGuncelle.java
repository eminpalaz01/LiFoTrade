package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infina.lifotrade.dto.AccountDto;
import com.infina.lifotrade.dto.request.AccountPutNameRequest;
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
public class HesapGuncelle implements Serializable {

	private static final long serialVersionUID = -4169100104484801596L;

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;

	private Long accountId;

	private String name;

	private AccountPutNameRequest accountPutNameRequest;

	private List<AccountDto> accountsDto;

	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	private AccountDto selectedAccountDto;

	private Long searchId = null;

	public void accountUpdate() {
		if (accountId != null) {
			accountPutNameRequest = new AccountPutNameRequest(name);
			Result result = lifotradeApi.put("/account/" + accountId, accountPutNameRequest);

			if (result.isSuccess()) {
				List<String> messages = new ArrayList<String>();
				messages.add("Güncelleme Başarılı");
				messageBean.showSuccessMessage(messages);
			} else {
				List<String> errorList = MessagesUtility.convertToMessages(result.getData());
				messageBean.showFailureMessage(errorList);
			}
			getAll();
		} else {
			List<String> nullMessages = new ArrayList<String>();
			nullMessages.add("Hesap seçilmedi.");
			messageBean.showFailureMessage(nullMessages);
		}
		getAll();
	}

	public void getAll() {
		Result result = lifotradeApi.get("/account");
		if (result.isSuccess()) {
			accountsDto = convertToAccountsDto(result.getData());
		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
		}
	}

	public void accountDtoRowSelected(SelectEvent<AccountDto> event) {
		accountId = selectedAccountDto.getId();
		name = selectedAccountDto.getName();
	}

	public void searchId() {
		if (searchId != null) {
			Result result = lifotradeApi.get("/account/" + searchId);
			if (result.isSuccess()) {
				List<String> messages = new ArrayList<String>();
				messages.add("Hesap getirildi.");
				messageBean.showSuccessMessage(messages);
				AccountDto accountDto = convertToAccountsDto(result);
				if (accountDto != null) {
					accountId = accountDto.getId();
					name = accountDto.getName();
				}
			} else {
				List<String> errorList = MessagesUtility.convertToMessages(result.getData());
				messageBean.showFailureMessage(errorList);
			}
		} else {
			List<String> nullMessages = new ArrayList<String>();
			nullMessages.add("Id alanı boş olamaz.");
			messageBean.showFailureMessage(nullMessages);
		}
	}

	public AccountDto convertToAccountsDto(Result result) {
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

	public List<AccountDto> convertToAccountsDto(String response) {
		List<AccountDto> accountListDto = null;
		try {
			accountListDto = objectMapper.readValue(response, new TypeReference<List<AccountDto>>() {
			});
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountListDto;
	}
}
