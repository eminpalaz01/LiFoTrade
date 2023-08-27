package com.infina.lifotradeapi.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// Yetkilendirme hatası durumunda HTTP 403 Forbidden durum kodu gönderilir
		response.sendError(HttpServletResponse.SC_FORBIDDEN,
				"Access Denied: You don't have permission to access this resource");
	}
}
