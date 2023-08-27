package com.infina.lifotradeapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AccountFundId implements Serializable {

	private static final long serialVersionUID = -3024312451540707347L;

	@Column(name = "account_id")
	private Long accountId;

	@Column(name = "fund_id")
	private Long fundId;

}
