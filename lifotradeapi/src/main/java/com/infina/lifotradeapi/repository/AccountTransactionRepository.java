package com.infina.lifotradeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infina.lifotradeapi.model.AccountTransaction;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {

	List<AccountTransaction> findByAccount_CustomerId(Long customerId);

	List<AccountTransaction> findByAccountId(Long accountId);

}
