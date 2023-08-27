package com.infina.lifotradeapi.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.infina.lifotradeapi.enums.FundTransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fund_transaction", indexes = @Index(name = "transaction_date_index", columnList = "transaction_date DESC"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundTransaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "amount")
	private BigDecimal amount;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "performed_by_id")
	private Employee performedBy;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "transaction_type")
	private FundTransactionType transactionType;

	@CreationTimestamp
	@Column(name = "transaction_date")
	private Date transactionDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fund_id")
	private Fund fund;
	
	@Column(name = "quantity")
	private Long quantity;
}
