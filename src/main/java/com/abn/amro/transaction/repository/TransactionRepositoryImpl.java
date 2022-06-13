package com.abn.amro.transaction.repository;

import com.abn.amro.transaction.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TransactionRepositoryImpl implements TransactionRepositoryCustom {

    private static final Logger LOG = LoggerFactory.getLogger(com.abn.amro.transaction.repository.TransactionRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }
}