package com.wex.ratexchg.service;

import com.wex.ratexchg.model.RateExchange;
import com.wex.ratexchg.repository.RateExchangeRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
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

    @Cacheable
    Optional<RateExchange> findByCountry(String country);

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
     * Convert a transaction amount with exchange rate passed in argument.
     *
     * @param rateExchange exchange rate
     * @param amount       Transaction to convert
     * @return Transaction amount converted with new exchange rate
     */
    BigDecimal convertAmount(RateExchange rateExchange, BigDecimal amount);

    /**
     * Sets a {@link RateExchangeRepository} instance.
     *
     * @param repository RateExchange repository instance
     */
    void setRepository(RateExchangeRepository repository);
}
