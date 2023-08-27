package com.infina.lifotradeapi.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.infina.lifotradeapi.exception.BusinessException;
import com.infina.lifotradeapi.exception.InsufficientBalanceException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "balance")
	private BigDecimal balance;

	@Column(name = "creation_date")
	@CreationTimestamp
	private Date creationDate;

	@Column(name = "update_date")
	@UpdateTimestamp
	private Date updateDate;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<FundTransaction> fundTransactions = new ArrayList<FundTransaction>();

	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<AccountFund> purchasedFunds = new ArrayList<>();

	public void deposit(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			List<String> messages = new ArrayList<String>();
			messages.add("Miktar negatif olamaz");
			throw new BusinessException(HttpStatus.BAD_REQUEST, messages);
		}

		balance = balance.add(amount);
	}

	public void withdraw(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			List<String> messages = new ArrayList<String>();
			messages.add("Miktar negatif olamaz");
			throw new BusinessException(HttpStatus.BAD_REQUEST, messages);
		}

		if (balance.compareTo(amount) < 0) {
			List<String> messages = new ArrayList<String>();
			messages.add("Yetersiz Bakiye");
			throw new InsufficientBalanceException(messages);
		}

		balance = balance.subtract(amount);

	}

}
