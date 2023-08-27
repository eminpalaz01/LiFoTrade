package com.infina.lifotradeapi.apidocs;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.infina.lifotradeapi.dto.EmployeeDto;
import com.infina.lifotradeapi.dto.request.EmployeePasswordChangeRequestDto;
import com.infina.lifotradeapi.dto.request.EmployeeUpdateRequestDto;
import com.infina.lifotradeapi.dto.request.PasswordResetMailRequestDto;

public interface EmployeeControllerApiDoc {
	
	ResponseEntity<List<EmployeeDto>> getAll();

	ResponseEntity<EmployeeDto> getByToken();

	ResponseEntity<EmployeeDto> getEmployeeById(Long employeeId);

	ResponseEntity<String> changePassword(@Valid EmployeePasswordChangeRequestDto changeRequest,
			BindingResult bindingResult);

	ResponseEntity<EmployeeDto> updateEmployee(Long employeeId, @Valid EmployeeUpdateRequestDto updateRequest,
			BindingResult bindingResult);

	ResponseEntity<String> resetPasswordWithEmail(@Valid PasswordResetMailRequestDto requestDto,
			BindingResult bindingResult);
}