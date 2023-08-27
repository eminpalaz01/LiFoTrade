package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class FonTanimlama implements Serializable {
	
	private static final long serialVersionUID = -865509310557163053L;

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;
	
	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	private String fundCode = "";
	private String isinCode = "";
	private String name = "";
	
	public void fundSaveRequest() {
		FundSaveRequestDto fundSaveRequestDto = new FundSaveRequestDto(fundCode, isinCode,name);
		
		Result result = lifotradeApi.post("/fund", fundSaveRequestDto);
		if(result.isSuccess()) {
			List<String> messages = new ArrayList<String>();
			messages.add("Fon ekleme başarılı");
			messageBean.showSuccessMessage(messages);
		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
		}
	}
}
