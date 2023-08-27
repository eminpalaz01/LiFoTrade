package com.infina.lifotradeapi.dto.converter;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.infina.lifotradeapi.dto.AccountDto;
import com.infina.lifotradeapi.dto.CorporateCustomerDto;
import com.infina.lifotradeapi.model.Account;
import com.infina.lifotradeapi.model.CorporateCustomer;

@Component
public class CorporateCustomerConverter {
 
    public CorporateCustomerDto convertToDto(CorporateCustomer corporateCustomer) {
        return new CorporateCustomerDto(corporateCustomer.getId(), 
                new ArrayList<AccountDto>(), 
                corporateCustomer.getAddress(), 
                corporateCustomer.getTaxNumber(), 
                corporateCustomer.getTitle());        
    }
    
    public CorporateCustomer convertToEntity(CorporateCustomerDto corporateCustomerDto) {
        return new CorporateCustomer(corporateCustomerDto.getId(), 
                new ArrayList<Account>(), 
                corporateCustomerDto.getAddress().trim(), 
                corporateCustomerDto.getTaxNumber().trim(), 
                corporateCustomerDto.getTitle().trim());        
    }
}
