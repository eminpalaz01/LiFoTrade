package com.infina.lifotradeapi.service.concretes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.infina.lifotradeapi.dto.EmployeeDto;
import com.infina.lifotradeapi.dto.converter.EmployeeConverter;
import com.infina.lifotradeapi.dto.request.EmployeeUpdateRequestDto;
import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.model.Employee;
import com.infina.lifotradeapi.repository.EmployeeRepository;
import com.infina.lifotradeapi.service.abstracts.EmailSendService;
import com.infina.lifotradeapi.service.abstracts.EmployeeService;
import com.infina.lifotradeapi.util.TemporaryPasswordGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeManager implements EmployeeService {

	private final EmployeeConverter employeeConverter;
	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;
	private final TemporaryPasswordGenerator temporaryPasswordGenerator;
	private final EmailSendService emailSendService;

	@Override
	public EmployeeDto getByUsernameAndPassword(String username, String password) {
		Employee employee = findByUsername(username);

		if (!passwordEncoder.matches(password, employee.getPassword())) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, Arrays.asList("Şifrenizi kontrol ediniz."));
		}
		return employeeConverter.convertToDto(employee);
	}

	public Employee findByUsername(String username) {
		Employee employee = employeeRepository.findByUsername(username).orElseThrow(
				() ->  new BusinessException(
						HttpStatus.NOT_FOUND, Arrays.asList("Sistemde böyle biri kayıtlı değildir.")));
		
		return employee;
	}

	@Override
	public EmployeeDto getByUsername(String username) {
		Employee employee = findByUsername(username);
		return employeeConverter.convertToDto(employee);
	}

	@Override
	public EmployeeDto save(EmployeeDto employeeDto) {
		if (existsByEmail(employeeDto)) {
			List<String> messages = new ArrayList<String>();
			messages.add("Bu Email kullanılmaktadır.");
			throw new BusinessException(HttpStatus.BAD_REQUEST, messages);
		}
		if (existsByUsername(employeeDto)) {
			List<String> messages = new ArrayList<String>();
			messages.add("Bu Kullanıcı adı kullanılmaktadır.");
			throw new BusinessException(HttpStatus.BAD_REQUEST, messages);
		}

		// Şifre algoritmadan geçirilerek saklanıcak
		employeeDto.setPassword(passwordEncoder.encode(employeeDto.getPassword().toString()));
		employeeDto.setIsLoginBefore(Boolean.FALSE);

		Employee employee = employeeConverter.convertToEntity(employeeDto);

		EmployeeDto savedEmployeeDto = employeeConverter.convertToDto(employeeRepository.save(employee));

		return savedEmployeeDto;
	}

	private Boolean existsByUsername(EmployeeDto employeeDto) {
		return employeeRepository.existsByUsername(employeeDto.getUsername());
	}

	private Boolean existsByEmail(EmployeeDto employeeDto) {
		return employeeRepository.existsByEmail(employeeDto.getEmail());
	}

	public List<EmployeeDto> getAll() {
		List<EmployeeDto> employeesDto = new ArrayList<>();
		List<Employee> employees = findAll();
		employees.forEach(employee -> {
			EmployeeDto employeeDto = employeeConverter.convertToDto(employee);
			employeesDto.add(employeeDto);
		});
		return employeesDto;
	}

	private List<Employee> findAll() {
		List<Employee> employees = employeeRepository.findAll();
		if (employees.size() == 0) {
			throw new BusinessException(HttpStatus.NOT_FOUND, Arrays.asList("Kayıtlı çalışan bulunamadı."));
		}
		return employees;
	}

	@Override
	public Employee findById(Long id) {
		return employeeRepository.findById(id).orElseThrow(
				() -> new BusinessException(HttpStatus.NOT_FOUND,Arrays.asList("Çalışan bulunamadı")));
	}

	@Override
	public void updatePassword(Employee toChanged, String newPassword) {
		String hashedPassword = passwordEncoder.encode(newPassword);
		toChanged.setPassword(hashedPassword);
		employeeRepository.save(toChanged);
	}

	@Override
	public EmployeeDto updateEmployee(Long employeeId, EmployeeUpdateRequestDto updateRequest) {
		Employee employeeToUpdate = findById(employeeId);
		employeeToUpdate.setName(updateRequest.getName());
		employeeToUpdate.setSurname(updateRequest.getSurname());
		employeeToUpdate.setUsername(updateRequest.getUsername());
		employeeToUpdate.setTitle(updateRequest.getTitle());
		employeeToUpdate.setEmail(updateRequest.getEmail());
		employeeToUpdate.setRole(updateRequest.getRole());

		employeeRepository.save(employeeToUpdate);

		return employeeConverter.convertToDto(employeeToUpdate);
	}

	@Override
	public EmployeeDto getEmployeeById(Long employeeId) {
		return employeeConverter.convertToDto(findById(employeeId));
	}

	@Override
	@Transactional
	public void requestPasswordReset(String email) {
		Employee employee = employeeRepository.findByEmail(email).orElseThrow(
				() -> new BusinessException(HttpStatus.NOT_FOUND, Arrays.asList("Kayıtlı e-posta bulunamadı")));

		String temporaryPassword = temporaryPasswordGenerator.generateTemporaryPassword();
		String hashedPassword = passwordEncoder.encode(temporaryPassword);
		employee.setPassword(hashedPassword);
		employee.setIsLoginBefore(Boolean.FALSE);
		employeeRepository.save(employee);

		emailSendService.sendPasswordResetMail(employee.getEmail(), temporaryPassword);

	}

	@Override
	public void updateIsLoginBefore(EmployeeDto employeeDto) {
		Employee employee = findById(employeeDto.getId());
		employee.setIsLoginBefore(Boolean.TRUE);
		employeeRepository.save(employee);
	}

}
