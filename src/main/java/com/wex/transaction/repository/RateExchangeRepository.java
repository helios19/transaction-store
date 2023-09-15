package com.wex.transaction.repository;

import com.wex.transaction.model.RateExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateExchangeRepository extends JpaRepository<RateExchange, Long>, RateExchangeRepositoryCustom {

}

