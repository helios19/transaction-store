package com.abn.amro.transaction.service;

import com.abn.amro.transaction.model.Transaction;
import com.abn.amro.transaction.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.abn.amro.common.utils.ClassUtils.TRANSACTIONS_COLLECTION_NAME;

/**
 * Service class providing CRUD operations and caching logic for {@link Transaction} resource.
 *
 * @see TransactionRepository
 */
@Service
@Slf4j
@CacheConfig(cacheNames = TRANSACTIONS_COLLECTION_NAME)
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository repository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CacheEvict(allEntries = true)
    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    public Optional<Transaction> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    public List<Transaction> findAll() {
        return repository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    public List<Transaction> findAll(Pageable pageable) {
        Page<Transaction> transactions = repository.findAll(pageable);
        return transactions.getContent();
    }

    /**
     * Sets an {@link TransactionRepository} instance.
     *
     * @param repository Transaction repository instance
     */
    public void setRepository(TransactionRepository repository) {
        this.repository = repository;
    }
}
