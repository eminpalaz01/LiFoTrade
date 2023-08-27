package com.infina.lifotradeapi.service.abstracts;

import com.infina.lifotradeapi.dto.CustomerDto;

public interface CustomerService {
	
	CustomerDto getById(Long customerId);

}
