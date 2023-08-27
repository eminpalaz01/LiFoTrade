package com.infina.lifotradeapi.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = -4419734012163003048L;
	
	private final HttpStatus httpStatus;

    public BusinessException(HttpStatus httpStatus, List<String> errorMessages) {
        super(errorMessages.toString());
        this.httpStatus = httpStatus;
    }

}
