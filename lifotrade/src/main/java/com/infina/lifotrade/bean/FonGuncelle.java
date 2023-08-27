package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infina.lifotrade.dto.FundDto;
import com.infina.lifotrade.dto.request.FundSaveRequestDto;
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
public class FonGuncelle implements Serializable {

	private static final long serialVersionUID = -4456376137637904058L;

	private String fundCode;

	private String isinCode;

	private String name;

	private BigDecimal currentPrice;

	private Date creationDate;

	private Date updateDate;

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;

	private FundSaveRequestDto fundSaveRequestDto;

	private List<FundDto> fundsDto;

	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	private FundDto selectedFundDto;

	private Long searchId = null;

	private Long fundId = null;

	public void fundUpdate() {
		if (fundId != null) {
			fundSaveRequestDto = new FundSaveRequestDto(fundCode, isinCode, name);
			Result result = lifotradeApi.put("/fund/" + fundId, fundSaveRequestDto);

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
			nullMessages.add("Fon seçilmedi.");
			messageBean.showFailureMessage(nullMessages);
		}
		getAll();
	}

	public void getAll() {
		Result result = lifotradeApi.get("/fund");
		if (result.isSuccess()) {
			fundsDto = convertToFundsDto(result.getData());
		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
		}
	}

	public void fundDtoRowSelected(SelectEvent<FundDto> event) {
		fundId = selectedFundDto.getId();
		fundCode = selectedFundDto.getFundCode();
		isinCode = selectedFundDto.getIsinCode();
		name = selectedFundDto.getName();
		currentPrice = selectedFundDto.getCurrentPrice();
		creationDate = selectedFundDto.getCreationDate();
		updateDate = selectedFundDto.getUpdateDate();
	}

	public void searchId() {
		if (searchId != null) {
			Result result = lifotradeApi.get("/fund/" + searchId);
			if (result.isSuccess()) {
				List<String> messages = new ArrayList<String>();
				messages.add("Fon getirildi.");
				messageBean.showSuccessMessage(messages);
				FundDto fundDto = convertToFundDto(result);
				if (fundDto != null) {
					fundId = fundDto.getId();
					fundCode = fundDto.getFundCode();
					isinCode = fundDto.getIsinCode();
					name = fundDto.getName();
					currentPrice = fundDto.getCurrentPrice();
					creationDate = fundDto.getCreationDate();
					updateDate = fundDto.getUpdateDate();
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

	public FundDto convertToFundDto(Result result) {
		FundDto fundDto = null;
		try {
			fundDto = objectMapper.readValue(result.getData(), FundDto.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fundDto;
	}

	public List<FundDto> convertToFundsDto(String response) {
		List<FundDto> fundListDto = null;
		try {
			fundListDto = objectMapper.readValue(response, new TypeReference<List<FundDto>>() {
			});
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fundListDto;
	}
}
