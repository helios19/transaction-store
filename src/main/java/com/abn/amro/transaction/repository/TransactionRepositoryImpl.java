package com.abn.amro.transaction.repository;

import com.abn.amro.transaction.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Repository implementation class methods to manipulate {@link Transaction} resource in database.
 * This class inherits from {@link TransactionRepositoryCustom}
 *
 * @see TransactionRepositoryCustom
 */
@Repository
@Slf4j
public class TransactionRepositoryImpl implements TransactionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }
}