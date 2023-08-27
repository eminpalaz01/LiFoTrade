package com.infina.lifotradeapi.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "corporate_customer")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class CorporateCustomer extends Customer {

	@Column(name = "tax_number", length = 10, unique = true)
	private String taxNumber;

	@Column(name = "title", length = 30)
	private String title;

	public CorporateCustomer() {
		super();
	}

	public CorporateCustomer(
			Long id, List<Account> accounts, String address, String taxNumber, String title) {
		super(id, accounts, address);
		this.taxNumber = taxNumber;
		this.title = title;
	}
}
