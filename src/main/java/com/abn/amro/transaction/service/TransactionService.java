package com.abn.amro.transaction.service;

import com.abn.amro.transaction.model.Transaction;
import com.abn.amro.transaction.repository.TransactionRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service interface providing method declarations for CRUD operations for the {@link Transaction} resource.
 *
 * @see Transaction
 * @see TransactionServiceImpl
 */
public interface TransactionService {
    /**
     * Saves an {@link Optional<Transaction>} instance.
     *
     * @param transaction Transaction object to save
     * @return Saved transaction
     */
    Transaction save(Transaction transaction);

    /**
     * Returns an {@link Optional<Transaction>} instance given {@code id} argument.
     *
     * @param id Transaction's identifier
     * @return Optional transaction
     */
    Optional<Transaction> findById(Long id);

    /**
     * Returns a list of all {@link Transaction}.
     *
     * @return List of transactions found
     */
    List<Transaction> findAll();

    /**
     * Return a list of {@link Transaction} given {@link Pageable} argument.
     *
     * @param pageable Pageable argument
     * @return List of transactions found
     */
    List<Transaction> findAll(Pageable pageable);

    /**
     * Sets a {@link TransactionRepository} instance.
     *
     * @param repository Transaction repository instance
     */
    void setRepository(TransactionRepository repository);
}
