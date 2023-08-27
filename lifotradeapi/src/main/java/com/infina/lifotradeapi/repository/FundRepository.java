package com.infina.lifotradeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infina.lifotradeapi.model.Fund;

@Repository
public interface FundRepository extends JpaRepository<Fund, Long> {
	
	Boolean existsByFundCode(String fundCode);

	Boolean existsByIsinCode(String isinCode);
}
