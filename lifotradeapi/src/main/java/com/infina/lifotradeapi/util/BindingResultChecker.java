package com.infina.lifotradeapi.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.infina.lifotradeapi.exception.BusinessException;

@Component
public class BindingResultChecker {
	
	public void checkBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            throw new BusinessException(HttpStatus.BAD_REQUEST, errors);
        }
    }
	
}
