package com.wex.ratexchg.repository;

import com.wex.ratexchg.model.RateExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RateExchangeRepository extends JpaRepository<RateExchange, Long>, RateExchangeRepositoryCustom {
    List<RateExchange> findByCountryAndRecordDateBetweenOrderByRecordDateDesc(String country, Date startDate, Date recordDate);
}

