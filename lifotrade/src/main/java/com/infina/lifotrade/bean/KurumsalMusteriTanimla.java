package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class KurumsalMusteriTanimla implements Serializable {

	private static final long serialVersionUID = -2575645632305640051L;

	private String taxNumber;

	private String title;
	
	private String address;

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;

	private CorporateCustomerSaveRequestDto corporateCustomerSaveRequestDto;

	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	public void corporateCustomerSave() {
		corporateCustomerSaveRequestDto = new CorporateCustomerSaveRequestDto(taxNumber, title, address);
		Result result = lifotradeApi.post("/corporate-customer", corporateCustomerSaveRequestDto);
		if (result.isSuccess()) {
			List<String> messages = new ArrayList<String>();
			messages.add("Kayıt Başarılı");
			messageBean.showSuccessMessage(messages);
		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
		}
	}
}
