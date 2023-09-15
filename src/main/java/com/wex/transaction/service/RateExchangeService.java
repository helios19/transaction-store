package com.wex.transaction.service;

import com.wex.transaction.model.RateExchange;
import com.wex.transaction.repository.RateExchangeRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service interface providing method declarations for CRUD operations for the {@link RateExchange} resource.
 *
 * @see RateExchange
 * @see RateExchangeServiceImpl
 */
public interface RateExchangeService {
    /**
     * Saves an {@link Optional<RateExchange>} instance.
     *
     * @param transaction RateExchange object to save
     * @return Saved transaction
     */
    RateExchange save(RateExchange transaction);

    /**
     * Returns an {@link Optional<RateExchange>} instance given {@code id} argument.
     *
     * @param id RateExchange's identifier
     * @return Optional transaction
     */
    Optional<RateExchange> findById(Long id);

    /**
     * Returns a list of all {@link RateExchange}.
     *
     * @return List of transactions found
     */
    List<RateExchange> findAll();

    /**
     * Return a list of {@link RateExchange} given {@link Pageable} argument.
     *
     * @param pageable Pageable argument
     * @return List of transactions found
     */
    List<RateExchange> findAll(Pageable pageable);

    /**
     * Sets a {@link RateExchangeRepository} instance.
     *
     * @param repository RateExchange repository instance
     */
    void setRepository(RateExchangeRepository repository);
}
