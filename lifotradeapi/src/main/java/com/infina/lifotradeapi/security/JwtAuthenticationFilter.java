package com.infina.lifotradeapi.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.model.Employee;
import com.infina.lifotradeapi.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final EmployeeRepository employeeRepository;
    private final JwtUtils jwtUtils;    
    
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String authHeader = request.getHeader("Authorization");
		final String userName;
		final String jwtToken;
		
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		jwtToken = authHeader.substring(7);
	
		userName = jwtUtils.extractUserName(jwtToken);
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			Employee employee = employeeRepository.findByUsername(userName).orElseThrow(
					() -> new BusinessException(HttpStatus.NOT_FOUND, Arrays.asList("Kullanıcı bulunamadı.")));			
			UserDetails userDetails = JwtUserDetails.create(employee);
			
			if(jwtUtils.isTokenValid(jwtToken, userDetails)){
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken
						(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
