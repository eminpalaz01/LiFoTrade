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
import com.infina.lifotrade.dto.FundTransactionDto;
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
public class FonAlimSatimIslemler implements Serializable{
	
	private static final long serialVersionUID = 6835540678725392567L;

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;
	
	private List<FundTransactionDto> fundTransactionsDto;

	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	private FundTransactionDto fundTransactionDto;

	private Long searchAccountId;

	private Long searchCustomerId;

	public void searchCustomerId() {
		if (searchCustomerId != null) {
			Result result = lifotradeApi.get("/fund-transaction/customer/" + searchCustomerId);
			if (result.isSuccess()) {
				fundTransactionsDto = convertToFundTransactionsDto(result.getData());
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
			Result result = lifotradeApi.get("/fund-transaction/account/" + searchAccountId);

			if (result.isSuccess()) {
				fundTransactionsDto = convertToFundTransactionsDto(result.getData());
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
		Result result = lifotradeApi.get("/fund-transaction");
		if (result.isSuccess()) {
			fundTransactionsDto = convertToFundTransactionsDto(result.getData());
		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
		}
	}

	public FundTransactionDto convertToFundTransactionDto(Result result) {
		FundTransactionDto fundTransactionDto = null;
		try {
			fundTransactionDto = objectMapper.readValue(result.getData(), FundTransactionDto.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fundTransactionDto;
	}

	public List<FundTransactionDto> convertToFundTransactionsDto(String response) {
		List<FundTransactionDto> fundTransactionListDto = null;
		try {
			fundTransactionListDto = objectMapper.readValue(response,
					new TypeReference<List<FundTransactionDto>>() {
					});
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fundTransactionListDto;
	}
}
