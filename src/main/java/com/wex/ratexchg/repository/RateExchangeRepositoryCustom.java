package com.wex.ratexchg.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public interface RateExchangeRepositoryCustom {
    EntityManager getEntityManager();
}
