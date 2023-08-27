package com.infina.lifotradeapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.infina.lifotradeapi.exception.BusinessException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fund")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fund {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fund_code", length = 3, unique = true)
	private String fundCode;
	
	@Column(name = "isin_code", length = 12, unique = true)
	private String isinCode;

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "current_price")
	private BigDecimal currentPrice;

	@Column(name = "creation_date")
	@CreationTimestamp
	private Date creationDate;

	@Column(name = "update_date")
	@UpdateTimestamp
	private Date updateDate;

	@JsonBackReference
	@OneToMany(mappedBy = "fund",  fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<FundTransaction> fundTransactions = new ArrayList<FundTransaction>();
	
	@OneToMany(mappedBy = "fund", fetch = FetchType.LAZY)
    private List<AccountFund> accounts = new ArrayList<>();
	
	public BigDecimal calculatePrice(Long quantity) {
        if (updateDate == null || !isToday(updateDate)) {
			List<String> messages = new ArrayList<String>();
			messages.add("Fon fiyatı bugün güncellenmemiş");
            throw new BusinessException(HttpStatus.BAD_REQUEST, messages);
        }
        
        return currentPrice.multiply(BigDecimal.valueOf(quantity));
    }
    
    private boolean isToday(Date date) {
        LocalDate currentDate = LocalDate.now();
        LocalDate givenDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return currentDate.equals(givenDate);
    }
}
