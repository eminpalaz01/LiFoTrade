package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.time.LocalDate;
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
import com.infina.lifotrade.dto.IndividualCustomerDto;
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
public class BireyselMusteriGuncelle implements Serializable {

	private static final long serialVersionUID = -8141928994445787723L;

	private Long individualCustomerId;

	private String address = "";

	private String identificationNumber = "";

	private String name = "";

	private String surname = "";

	private String gender = "";

	private LocalDate dateOfBirth = LocalDate.now();

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;

	private IndividualCustomerSaveRequestDto individualCustomerSaveRequestDto;

	private List<IndividualCustomerDto> individualCustomersDto;

	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	private IndividualCustomerDto selectedIndividualCustomerDto;

	private Long searchId = null;

	public void individualCustomerUpdate() {
		if (individualCustomerId != null) {
			individualCustomerSaveRequestDto = new IndividualCustomerSaveRequestDto(address, identificationNumber, name,
					surname, gender, dateOfBirth);
			Result result = lifotradeApi.put("/individual-customer/" + individualCustomerId,
					individualCustomerSaveRequestDto);

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
		Result result = lifotradeApi.get("/individual-customer");
		if (result.isSuccess()) {
			individualCustomersDto = convertToIndividualCustomersDto(result.getData());
		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
		}
	}

	public void individualCustomerDtoRowSelected(SelectEvent<IndividualCustomerDto> event) {
		individualCustomerId = selectedIndividualCustomerDto.getId();
		address = selectedIndividualCustomerDto.getAddress();
		identificationNumber = selectedIndividualCustomerDto.getIdentificationNumber();
		name = selectedIndividualCustomerDto.getName();
		surname = selectedIndividualCustomerDto.getSurname();
		gender = selectedIndividualCustomerDto.getGender();
		dateOfBirth = selectedIndividualCustomerDto.getDateOfBirth();
	}

	public void searchId() {
		if (searchId != null) {
			Result result = lifotradeApi.get("/individual-customer/" + searchId);
			if (result.isSuccess()) {
				List<String> messages = new ArrayList<String>();
				messages.add("Kullanıcı getirildi.");
				messageBean.showSuccessMessage(messages);
				IndividualCustomerDto individualCustomerDto = convertToIndividualCustomerDto(result);
				if (individualCustomerDto != null) {
					individualCustomerId = individualCustomerDto.getId();
					address = individualCustomerDto.getAddress();
					identificationNumber = individualCustomerDto.getIdentificationNumber();
					name = individualCustomerDto.getName();
					surname = individualCustomerDto.getSurname();
					gender = individualCustomerDto.getGender();
					dateOfBirth = individualCustomerDto.getDateOfBirth();
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

	public IndividualCustomerDto convertToIndividualCustomerDto(Result result) {
		IndividualCustomerDto individualCustomerDto = null;
		try {
			individualCustomerDto = objectMapper.readValue(result.getData(), IndividualCustomerDto.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return individualCustomerDto;
	}

	public List<IndividualCustomerDto> convertToIndividualCustomersDto(String response) {
		List<IndividualCustomerDto> individualCustomerListDto = null;
		try {
			individualCustomerListDto = objectMapper.readValue(response,
					new TypeReference<List<IndividualCustomerDto>>() {
					});
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return individualCustomerListDto;
	}
}
