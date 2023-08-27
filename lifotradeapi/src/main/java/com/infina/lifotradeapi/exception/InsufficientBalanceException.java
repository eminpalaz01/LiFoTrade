package com.infina.lifotradeapi.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

	@ResponseStatus(HttpStatus.FORBIDDEN) 
	public class InsufficientBalanceException extends RuntimeException {
	
		private static final long serialVersionUID = 1L;
	
		public InsufficientBalanceException(List<String> message) {
	        super(message.toString());
	    }
	}
