package com.infina.lifotradeapi.dto.converter;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.infina.lifotradeapi.dto.CustomerDto;
import com.infina.lifotradeapi.model.Customer;


@Component
public class CustomerConverter {
	
    public CustomerDto convertToDto(Customer customer) {
        return new CustomerDto(customer.getId(),
        		new ArrayList<>(), 
                customer.getAddress());        
    }
    
    public Customer convertToEntity(CustomerDto customerDto) {
        return new Customer(customerDto.getId(), 
        		new ArrayList<>(), 
                customerDto.getAddress().trim());        
    }
}
