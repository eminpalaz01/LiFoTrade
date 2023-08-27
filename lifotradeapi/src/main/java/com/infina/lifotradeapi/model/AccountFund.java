package com.infina.lifotradeapi.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.springframework.http.HttpStatus;

import com.infina.lifotradeapi.exception.BusinessException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account_fund")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountFund {

	@EmbeddedId
	private AccountFundId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("account_id")
	@JoinColumn(name = "account_id")
	private Account account;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("fund_id")
	@JoinColumn(name = "fund_id")
	private Fund fund;

	@Column(name = "quantity")
	private Long quantity;

	public void purchaseQuantity(Long quantity) {
		if (quantity <= 0) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, Arrays.asList("Adet değeri pozitif girilmelidir"));
		}
		if (this.quantity == null) {
			this.quantity = quantity;
		} else {
			this.quantity += quantity;
		}
	}

	public void sellQuantity(Long quantity) {
		if (quantity <= 0) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, Arrays.asList("Adet değeri pozitif girilmelidir"));
		}
		if (quantity > this.quantity) {
			throw new BusinessException(HttpStatus.FORBIDDEN, Arrays.asList("Yeterli adet bulunmamaktadır"));
		}
		this.quantity -= quantity;
	}
}
