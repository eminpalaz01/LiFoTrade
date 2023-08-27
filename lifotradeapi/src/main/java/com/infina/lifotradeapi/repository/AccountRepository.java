package com.infina.lifotradeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infina.lifotradeapi.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
