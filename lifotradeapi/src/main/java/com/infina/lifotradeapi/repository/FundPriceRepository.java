package com.infina.lifotradeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infina.lifotradeapi.model.FundPrice;

@Repository
public interface FundPriceRepository extends JpaRepository<FundPrice, Long> {

}
