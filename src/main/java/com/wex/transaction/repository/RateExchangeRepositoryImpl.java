package com.wex.transaction.repository;

import com.wex.transaction.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Repository implementation class methods to manipulate {@link Transaction} resource in database.
 * This class inherits from {@link RateExchangeRepositoryCustom}
 *
 * @see RateExchangeRepositoryCustom
 */
@Repository
@Slf4j
public class RateExchangeRepositoryImpl implements RateExchangeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }
}