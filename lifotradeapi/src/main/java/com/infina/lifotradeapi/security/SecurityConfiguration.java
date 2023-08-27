package com.infina.lifotradeapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.infina.lifotradeapi.repository.EmployeeRepository;
import com.infina.lifotradeapi.service.concretes.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint handler;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final EmployeeRepository employeeRepository;
	
	public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
			JwtAuthenticationEntryPoint handler, 
			 EmployeeRepository employeeRepository, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.handler = handler;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
		this.employeeRepository = employeeRepository;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
		.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers("/swagger-ui/index.html",
				     "/swagger-ui/swagger-ui.css",
				     "/swagger-ui/swagger-ui-bundle.js",
				     "/swagger-ui/swagger-ui-standalone-preset.js",
				     "/swagger-ui/swagger-initializer.js",
				     "/swagger-ui/index.css",
				     "/swagger-ui/favicon-32x32.png",
				     "/swagger-ui/favicon-16x16.png",
				     "/v3/api-docs/swagger-config",
				     "/v3/api-docs").permitAll()		
		.and()
		.authorizeRequests()
		.antMatchers("/api/v1/auth/login").permitAll()
		.and()
		.authorizeRequests()
		.antMatchers("/api/v1/auth/register").permitAll()
		.and()
		.authorizeRequests()
		.antMatchers("/api/v1/employee/reset-password-mail").permitAll()
		.and()
		.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.headers(headers -> headers.frameOptions().sameOrigin())
		.exceptionHandling().authenticationEntryPoint(handler)
		.and()
		.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authenticationProvider(authenticationProvider())
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		// TODO Auto-generated method stub
		final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		// buraya bak.
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl(employeeRepository); 	
	}

}
