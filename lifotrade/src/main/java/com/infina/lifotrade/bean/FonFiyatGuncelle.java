package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.modelmapper.ModelMapper;
import org.primefaces.event.SelectEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infina.lifotrade.dto.FundDto;
import com.infina.lifotrade.dto.FundPriceDto;
import com.infina.lifotrade.dto.request.FundPriceSaveRequestDto;
import com.infina.lifotrade.services.common.LifotradeApi;
import com.infina.lifotrade.services.common.MessagesUtility;
import com.infina.lifotrade.services.common.ModelMapperSingleton;
import com.infina.lifotrade.services.common.ObjectMapperSingleton;
import com.infina.lifotrade.services.common.Result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ViewScoped
@ManagedBean
public class FonFiyatGuncelle implements Serializable {

	private static final long serialVersionUID = 8118358264479099305L;

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;

	private FundPriceSaveRequestDto FundPriceSaveRequestDto;

	private List<FundDto> fundsDto;

	private List<FundPriceSaveRequestDto> fundPriceSaveRequestsDto;

	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	private ModelMapper modelMapper = ModelMapperSingleton.getInstance();

	private FundPriceDto selectedFundPriceDto;

	public void saveAll() {
		if (fundsDto.size() > 0) {
			fundPriceSaveRequestsDto = convertToFundPriceSaveRequestsDto(fundsDto);
			Result result = lifotradeApi.post("/fund-price/save-all", fundPriceSaveRequestsDto);

			if (result.isSuccess()) {
				List<String> messages = new ArrayList<String>();
				messages.add("Fiyatlar Güncellendi.");
				messageBean.showSuccessMessage(messages);
			} else {
				List<String> errorList = MessagesUtility.convertToMessages(result.getData());
				messageBean.showFailureMessage(errorList);
			}

			getAll();
		} else {
			List<String> errorList = new ArrayList<String>();
			errorList.add("Liste boş.");
			messageBean.showFailureMessage(errorList);
		}
	}

	public void getAll() {
		Result result = lifotradeApi.get("/fund");
		if (result.isSuccess()) {
			List<String> messages = new ArrayList<String>();
			messages.add("Fonlar getirildi.");
			messageBean.showSuccessMessage(messages);
			fundsDto = convertToFundsDto(result.getData());
		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
		}
	}

	public void fundDtoRowSelected(SelectEvent<FundDto> event) {
		return;
	}

	public FundPriceDto convertToFundPriceDto(Result result) {
		FundPriceDto fundPriceDto = null;
		try {
			fundPriceDto = objectMapper.readValue(result.getData(), FundPriceDto.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fundPriceDto;
	}

	public List<FundPriceDto> convertToFundPricesDto(String response) {
		List<FundPriceDto> fundPriceListDto = null;
		try {
			fundPriceListDto = objectMapper.readValue(response, new TypeReference<List<FundPriceDto>>() {
			});
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fundPriceListDto;
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

	public List<FundPriceSaveRequestDto> convertToFundPriceSaveRequestsDto(List<FundDto> fundsDto) {
		List<FundPriceSaveRequestDto> fundPriceSaveRequestListDto = new ArrayList<FundPriceSaveRequestDto>();

		fundPriceSaveRequestListDto = fundsDto.stream().map(fundPriceDto -> {
			return modelMapper.map(fundPriceDto, FundPriceSaveRequestDto.class);
		}).collect(Collectors.toList());

		return fundPriceSaveRequestListDto;
	}
}
