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
import com.infina.lifotrade.dto.EmployeeDto;
import com.infina.lifotrade.dto.request.EmployeeUpdateRequestDto;
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
public class KullaniciGuncelle implements Serializable {

	private static final long serialVersionUID = -3319173265229840225L;

	private Long userId;

	private String name = "";

	private String surname = "";

	private String username = "";

	private String title = "";

	private String email = "";

	private String role = "";

	@ManagedProperty(value = "#{lifotradeApi}")
	private LifotradeApi<?> lifotradeApi;

	@ManagedProperty(value = "#{messageBean}")
	private MessageBean messageBean;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	private EmployeeUpdateRequestDto employeeUpdateRequestDto;

	private List<EmployeeDto> employeesDto;

	private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

	private EmployeeDto selectedEmployee;

	private Long searchId = null;

	public void userUpdate() {
		if (userBean.getEmployeeDto().getId() == userId) {
			List<String> errorList = new ArrayList<String>();
			errorList.add("Kullanıcı Kendisini güncelleyemez.");
			messageBean.showFailureMessage(errorList);
			return;
		}

		if (userId != null) {
			employeeUpdateRequestDto = new EmployeeUpdateRequestDto(name, surname, username, title, email, role);
			Result result = lifotradeApi.put("/employee/" + userId, employeeUpdateRequestDto);
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
			nullMessages.add("Kullanıcı seçilmedi.");
			messageBean.showFailureMessage(nullMessages);
		}

	}

	public void getAll() {
		Result result = lifotradeApi.get("/employee");
		if (result.isSuccess()) {
			employeesDto = convertToEmployeeListDto(result.getData());
		} else {
			List<String> errorList = MessagesUtility.convertToMessages(result.getData());
			messageBean.showFailureMessage(errorList);
		}
	}

	public void employeeDtoRowSelected(SelectEvent<EmployeeDto> event) {
		userId = selectedEmployee.getId();
		name = selectedEmployee.getName();
		username = selectedEmployee.getUsername();
		surname = selectedEmployee.getSurname();
		email = selectedEmployee.getEmail();
		title = selectedEmployee.getTitle();
		role = selectedEmployee.getRole();
	}

	public void searchId() {
		if (searchId != null) {
			Result result = lifotradeApi.get("/employee/" + searchId);
			if (result.isSuccess()) {
				List<String> messages = new ArrayList<String>();
				messages.add("Kullanıcı getirildi.");
				messageBean.showSuccessMessage(messages);
				EmployeeDto employee = convertToEmployeeDto(result);
				if (employee != null) {
					userId = employee.getId();
					name = employee.getName();
					username = employee.getUsername();
					surname = employee.getSurname();
					email = employee.getEmail();
					title = employee.getTitle();
					role = employee.getRole();
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

	public EmployeeDto convertToEmployeeDto(Result result) {
		EmployeeDto employeeDto = null;
		try {
			employeeDto = objectMapper.readValue(result.getData(), EmployeeDto.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employeeDto;
	}

	public List<EmployeeDto> convertToEmployeeListDto(String response) {
		List<EmployeeDto> employeeList = new ArrayList<EmployeeDto>();
		try {
			employeeList = objectMapper.readValue(response, new TypeReference<List<EmployeeDto>>() {
			});
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employeeList;
	}
}
