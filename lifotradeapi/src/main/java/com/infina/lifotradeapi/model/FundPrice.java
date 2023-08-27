package com.infina.lifotradeapi.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fund_price")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundPrice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "fund_id")
	private Fund fund;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "price_changing_date")
	@CreationTimestamp
	private Date priceChangingDate;
	
}
