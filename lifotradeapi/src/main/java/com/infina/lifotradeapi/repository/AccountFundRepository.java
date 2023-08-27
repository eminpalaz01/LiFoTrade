package com.infina.lifotradeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infina.lifotradeapi.model.AccountFund;
import com.infina.lifotradeapi.model.AccountFundId;

public interface AccountFundRepository extends JpaRepository<AccountFund, AccountFundId>{
	AccountFund findByAccountIdAndFundId(Long accountId, Long fundId);

	List<AccountFund> findAllByAccountId(Long accountId);
}
