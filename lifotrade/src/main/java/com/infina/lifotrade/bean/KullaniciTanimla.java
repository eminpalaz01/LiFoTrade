package com.infina.lifotrade.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infina.lifotrade.dto.request.RegisterRequestDto;
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
public class KullaniciTanimla implements Serializable {

	private static final long serialVersionUID = -1986894820711265998L;

	private String name = "";

	private String surname = "";

	private String username = "";

	private String title = "";

	private String password = "";

	private String email = "";

	private String role = "";

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;

	private RegisterRequestDto registerRequestDto;

	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	public void userSave() {
		registerRequestDto = new RegisterRequestDto(name, surname, username, title, password, email, role);
		Result result = lifotradeApi.post("/auth/register", registerRequestDto);
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
