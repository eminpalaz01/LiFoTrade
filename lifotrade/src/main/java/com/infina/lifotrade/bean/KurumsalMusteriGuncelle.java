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
import com.infina.lifotrade.dto.CorporateCustomerDto;
import com.infina.lifotrade.dto.IndividualCustomerDto;
import com.infina.lifotrade.dto.request.CorporateCustomerSaveRequestDto;
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
public class KurumsalMusteriGuncelle implements Serializable {

	private static final long serialVersionUID = -8917390492879771049L;

	private Long corporateCustomerId;

	private String address = "";

	private String taxNumber = "";

	private String title = "";

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;

	private CorporateCustomerSaveRequestDto corporateCustomerSaveRequestDto;

	private List<CorporateCustomerDto> corporateCustomersDto;

	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	private CorporateCustomerDto selectedCorporateCustomerDto;

	private Long searchId = null;

	public void corporateCustomerUpdate() {
		if (corporateCustomerId != null) {
			corporateCustomerSaveRequestDto = new CorporateCustomerSaveRequestDto(taxNumber, title, address);
			Result result = lifotradeApi.put("/corporate-customer/" + corporateCustomerId,
					corporateCustomerSaveRequestDto);

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
			nullMessages.add("Müşteri seçilmedi.");
			messageBean.showFailureMessage(nullMessages);
		}
		getAll();
	}

	public void getAll() {
		Result result = lifotradeApi.get("/corporate-customer");
		if (result.isSuccess()) {
			corporateCustomersDto = convertToCorporateCustomersDto(result.getData());
		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
		}
	}

	public void corporateCustomerDtoRowSelected(SelectEvent<IndividualCustomerDto> event) {
		corporateCustomerId = selectedCorporateCustomerDto.getId();
		address = selectedCorporateCustomerDto.getAddress();
		taxNumber = selectedCorporateCustomerDto.getTaxNumber();
		title = selectedCorporateCustomerDto.getTitle();
	}

	public void searchId() {
		if (searchId != null) {
			Result result = lifotradeApi.get("/corporate-customer/" + searchId);
			if (result.isSuccess()) {
				List<String> messages = new ArrayList<String>();
				messages.add("Kullanıcı getirildi.");
				messageBean.showSuccessMessage(messages);
				CorporateCustomerDto corporateCustomerDto = convertToCorporateCustomerDto(result);
				if (corporateCustomerDto != null) {
					corporateCustomerId = corporateCustomerDto.getId();
					address = corporateCustomerDto.getAddress();
					taxNumber = corporateCustomerDto.getTaxNumber();
					title = corporateCustomerDto.getTitle();
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

	public CorporateCustomerDto convertToCorporateCustomerDto(Result result) {
		CorporateCustomerDto corporateCustomerDto = null;
		try {
			corporateCustomerDto = objectMapper.readValue(result.getData(), CorporateCustomerDto.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return corporateCustomerDto;
	}

	public List<CorporateCustomerDto> convertToCorporateCustomersDto(String response) {
		List<CorporateCustomerDto> corporateCustomerListDto = null;
		try {
			corporateCustomerListDto = objectMapper.readValue(response,
					new TypeReference<List<CorporateCustomerDto>>() {
					});
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return corporateCustomerListDto;
	}
}
