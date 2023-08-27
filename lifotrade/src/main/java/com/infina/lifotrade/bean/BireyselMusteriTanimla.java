package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infina.lifotrade.dto.request.IndividualCustomerSaveRequestDto;
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
public class BireyselMusteriTanimla implements Serializable {

	private static final long serialVersionUID = -7723463432103264632L;
	
	private String address;

	private String identificationNumber;

	private String name;

	private String surname;

	private String gender;

	private LocalDate dateOfBirth = LocalDate.now();

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;

	private IndividualCustomerSaveRequestDto individualCustomerSaveRequestDto;

	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	public void individualCustomerSave() {
		individualCustomerSaveRequestDto = new IndividualCustomerSaveRequestDto
				(address, identificationNumber, name, surname, gender, dateOfBirth);
		Result result = lifotradeApi.post("/individual-customer", individualCustomerSaveRequestDto);
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
