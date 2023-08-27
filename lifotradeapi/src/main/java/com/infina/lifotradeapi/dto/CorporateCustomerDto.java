package com.infina.lifotradeapi.dto;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class CorporateCustomerDto extends CustomerDto {

	private String taxNumber;

	private String title;

	public CorporateCustomerDto(Long id,
            List<AccountDto> accounts, String address, String taxNumber, String title) {
            super(id, accounts, address);
            this.taxNumber = taxNumber;
            this.title = title;
     }

}
