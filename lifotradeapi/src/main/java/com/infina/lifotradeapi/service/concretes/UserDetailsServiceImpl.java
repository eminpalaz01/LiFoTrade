package com.infina.lifotradeapi.service.concretes;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.model.Employee;
import com.infina.lifotradeapi.repository.EmployeeRepository;
import com.infina.lifotradeapi.security.JwtUserDetails;

public class UserDetailsServiceImpl implements UserDetailsService {

	private final EmployeeRepository employeeRepository;
	
	public UserDetailsServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Employee employeeDto = employeeRepository.findByUsername(userName).orElseThrow(
				() -> new BusinessException(HttpStatus.NOT_FOUND, Arrays.asList("Kullanıcı bulunamadı.")));
		return JwtUserDetails.create(employeeDto);
	}

}
