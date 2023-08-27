package com.infina.lifotradeapi.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.setContentType("application/text");
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	       
	    response.getWriter().write
	    (new ArrayList<String>
	    (Arrays.asList("Yetkiniz yoktur.")).toString());
	    
	    System.out.println(response.getStatus());
	}

}
