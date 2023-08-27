package com.infina.lifotradeapi.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infina.lifotradeapi.dto.EmployeeDto;
import com.infina.lifotradeapi.dto.converter.EmployeeConverter;
import com.infina.lifotradeapi.dto.request.AuthenticateRequestDto;
import com.infina.lifotradeapi.dto.request.RegisterRequestDto;
import com.infina.lifotradeapi.enums.Role;
import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.security.JwtUserDetails;
import com.infina.lifotradeapi.security.JwtUtils;
import com.infina.lifotradeapi.service.abstracts.EmployeeService;
import com.infina.lifotradeapi.util.BindingResultChecker;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final EmployeeService employeeService;
	private final EmployeeConverter employeeConverter;
	private final ModelMapper modelMapper;
	private final BindingResultChecker bindingResultChecker;

	// EmployeeControllerApiDoc
	@PostMapping("/login")
	public ResponseEntity<String> authenticate(@Valid @RequestBody AuthenticateRequestDto request, BindingResult bindingResult) {
		bindingResultChecker.checkBindingResult(bindingResult);
		
		final UserDetails user = JwtUserDetails.create(employeeConverter.convertToEntity(
				employeeService.getByUsernameAndPassword(request.getUsername(), request.getPassword())));
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(auth);
		return ResponseEntity.ok(jwtUtils.generateToken(user));
	}

	@PostMapping("/register")
	public ResponseEntity<EmployeeDto> register(@Valid @RequestBody RegisterRequestDto request,
			BindingResult bindingResult) {
		bindingResultChecker.checkBindingResult(bindingResult);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		EmployeeDto userEmployee = employeeService.getByUsername(authentication.getName());
		if (!userEmployee.getRole().equals(Role.ADMIN) && request.getRole().equals(Role.ADMIN)) {
			throw new BusinessException(HttpStatus.FORBIDDEN, Arrays.asList("Admin yetkiniz yoktur"));
		}
		return new ResponseEntity<>(employeeService.save(modelMapper.map(request, EmployeeDto.class)),
				HttpStatus.CREATED);
	}

}
