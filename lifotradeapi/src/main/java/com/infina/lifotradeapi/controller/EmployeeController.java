package com.infina.lifotradeapi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infina.lifotradeapi.apidocs.EmployeeControllerApiDoc;
import com.infina.lifotradeapi.dto.EmployeeDto;
import com.infina.lifotradeapi.dto.request.EmployeePasswordChangeRequestDto;
import com.infina.lifotradeapi.dto.request.EmployeeUpdateRequestDto;
import com.infina.lifotradeapi.dto.request.PasswordResetMailRequestDto;
import com.infina.lifotradeapi.enums.Role;
import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.model.Employee;
import com.infina.lifotradeapi.service.abstracts.EmployeeService;
import com.infina.lifotradeapi.util.BindingResultChecker;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController implements EmployeeControllerApiDoc {

	private final EmployeeService employeeService;
	private final PasswordEncoder passwordEncoder;
	private final BindingResultChecker bindingResultChecker;

	@GetMapping
	@Override
	public ResponseEntity<List<EmployeeDto>> getAll() {
		return ResponseEntity.ok(employeeService.getAll());
	}

	@GetMapping("/me")
	@Override
	public ResponseEntity<EmployeeDto> getByToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		EmployeeDto employeeDto = employeeService.getByUsername(auth.getName());

		if (!employeeDto.getIsLoginBefore()) {
			employeeService.updateIsLoginBefore(employeeDto);
			throw new BusinessException(HttpStatus.LOCKED,
					new ArrayList<String>(Arrays.asList("Şifrenizi değiştiriniz.")));
		}

		return ResponseEntity.ok(employeeDto);
	}

	@PutMapping("/{employeeId}")
	@Override
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long employeeId,
			@Valid @RequestBody EmployeeUpdateRequestDto updateRequest, BindingResult bindingResult) {

		bindingResultChecker.checkBindingResult(bindingResult);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		EmployeeDto userEmployee = employeeService.getByUsername(authentication.getName());
		if (!userEmployee.getRole().equals(Role.ADMIN) && updateRequest.getRole().equals(Role.ADMIN)) {
			throw new BusinessException(HttpStatus.FORBIDDEN, Arrays.asList("Admin yetkiniz yoktur"));
		}
		EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeId, updateRequest);
		return ResponseEntity.ok(updatedEmployee);
	}

	@PostMapping("/change-password")
	@Override
	public ResponseEntity<String> changePassword(@Valid @RequestBody EmployeePasswordChangeRequestDto changeRequest,
			BindingResult bindingResult) {

		bindingResultChecker.checkBindingResult(bindingResult);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		if (username.equals("anonymousUser")) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, Arrays.asList("Giriş yapılmamış"));
		}
		Employee toChanged = employeeService.findByUsername(username);

		if (!passwordEncoder.matches(changeRequest.getOldPassword(), toChanged.getPassword())) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, Arrays.asList("Şifre Hatalı"));

		}

		if (!changeRequest.getNewPassword().equals(changeRequest.getConfirmNewPassword())) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, Arrays.asList("Yeni şifreler uyuşmuyor"));
		}

		employeeService.updatePassword(toChanged, changeRequest.getNewPassword());

		return ResponseEntity.ok("Şifre değiştirme başarılı");
	}

	@Override
	@PostMapping("/reset-password-mail")
	public ResponseEntity<String> resetPasswordWithEmail(@Valid @RequestBody PasswordResetMailRequestDto requestDto,
			BindingResult bindingResult) {
		
		bindingResultChecker.checkBindingResult(bindingResult);

		employeeService.requestPasswordReset(requestDto.getEmail());
		return ResponseEntity.ok("Şifre sıfırlama e-postası gönderildi.");
	}

	@Override
	@GetMapping("/{employeeId}")
	public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long employeeId) {
		return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
	}

}
