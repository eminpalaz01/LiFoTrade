package com.infina.lifotradeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infina.lifotradeapi.model.FundTransaction;

@Repository
public interface FundTransactionRepository extends JpaRepository<FundTransaction, Long> {

	List<FundTransaction> findAllByAccountId(Long accountId);

	List<FundTransaction> findByAccount_CustomerId(Long customerId);

}
