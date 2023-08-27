package com.infina.lifotradeapi.service.abstracts;

import java.util.List;

import com.infina.lifotradeapi.dto.EmployeeDto;
import com.infina.lifotradeapi.dto.request.EmployeeUpdateRequestDto;
import com.infina.lifotradeapi.model.Employee;

public interface EmployeeService {

	EmployeeDto getByUsername(String username);
	
	Employee findByUsername(String username);

	EmployeeDto getByUsernameAndPassword(String username, String password);

	EmployeeDto save(EmployeeDto userDto);

	List<EmployeeDto> getAll();

	Employee findById(Long id);

	void updatePassword(Employee toChanged, String newPassword);

	EmployeeDto updateEmployee(Long employeeId, EmployeeUpdateRequestDto updateRequest);

	EmployeeDto getEmployeeById(Long employeeId);
	
	void requestPasswordReset(String email);
	
	void updateIsLoginBefore(EmployeeDto employeeDto);	
}
