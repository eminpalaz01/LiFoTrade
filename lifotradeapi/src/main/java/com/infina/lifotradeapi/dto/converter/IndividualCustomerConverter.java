package com.infina.lifotradeapi.dto.converter;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.infina.lifotradeapi.dto.AccountDto;
import com.infina.lifotradeapi.dto.IndividualCustomerDto;
import com.infina.lifotradeapi.model.Account;
import com.infina.lifotradeapi.model.IndividualCustomer;

@Component
public class IndividualCustomerConverter {    

    public IndividualCustomerDto convertToDto(IndividualCustomer individualCustomer) {
    	
        return new IndividualCustomerDto(individualCustomer.getId(),
        		new ArrayList<AccountDto>(), 
                individualCustomer.getAddress(), 
                individualCustomer.getIdentificationNumber(), 
                individualCustomer.getName(), 
                individualCustomer.getSurname(), 
                individualCustomer.getGender(),
                individualCustomer.getDateOfBirth());        
    }
    
    public IndividualCustomer convertToEntity(IndividualCustomerDto individualCustomerDto) {
        return new IndividualCustomer(individualCustomerDto.getId(), 
                new ArrayList<Account>(), 
                individualCustomerDto.getAddress().trim(), 
                individualCustomerDto.getIdentificationNumber().trim(), 
                individualCustomerDto.getName().trim(), 
                individualCustomerDto.getSurname().trim(), 
                individualCustomerDto.getGender(),
                individualCustomerDto.getDateOfBirth());        
    }
}
