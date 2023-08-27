package com.infina.lifotradeapi.dto.converter;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.infina.lifotradeapi.dto.EmployeeDto;
import com.infina.lifotradeapi.model.Employee;


@Component
public class EmployeeConverter {
		
	public EmployeeDto convertToDto(Employee employee) {
		return new EmployeeDto(employee.getId(), 
				employee.getName(), 
				employee.getSurname(), 
				employee.getUsername(),
				employee.getTitle(),
				employee.getPassword(), 
				employee.getEmail(),
				employee.getIsLoginBefore(),
				employee.getRole(),
				new ArrayList<>(),
				new ArrayList<>()); 		
	}
	
	public Employee convertToEntity(EmployeeDto employeeDto) {
		return new Employee(employeeDto.getId(), 
				employeeDto.getName().trim(), 
				employeeDto.getSurname().trim(), 
				employeeDto.getUsername().trim(),
				employeeDto.getTitle().trim(),
				employeeDto.getPassword().trim(), 
				employeeDto.getEmail().trim(), 
				employeeDto.getIsLoginBefore(),
				employeeDto.getRole(),			
				new ArrayList<>(),				
				new ArrayList<>()); 	
	}
}
