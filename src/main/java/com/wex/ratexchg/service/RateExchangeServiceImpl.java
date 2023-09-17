package com.wex.ratexchg.service;

import com.wex.common.utils.ClassUtils;
import com.wex.ratexchg.model.RateExchange;
import com.wex.ratexchg.repository.RateExchangeRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service class providing CRUD operations and caching logic for {@link RateExchange} resource.
 *
 * @see RateExchangeRepository
 */
@Service
@Slf4j
@CacheConfig(cacheNames = ClassUtils.RATE_EXCHANGE_COLLECTION_NAME)
@NoArgsConstructor
public class RateExchangeServiceImpl implements RateExchangeService {

    private RateExchangeRepository repository;

    @Autowired
    public RateExchangeServiceImpl(RateExchangeRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CacheEvict(allEntries = true)
    public RateExchange save(RateExchange rateExchange) {
        return repository.save(rateExchange);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    public Optional<RateExchange> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    public Optional<RateExchange> findByCountry(String country) {
        List<RateExchange> rateExchanges = repository.findByCountryOrderByRecordDateDesc(country);

        if (CollectionUtils.isEmpty(rateExchanges)) {
            return Optional.empty();
        }

        return rateExchanges.stream().findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    public List<RateExchange> findAll() {
        return repository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable
    public List<RateExchange> findAll(Pageable pageable) {
        Page<RateExchange> rateExchanges = repository.findAll(pageable);
        return rateExchanges.getContent();
    }

    /**
     * Convert a transaction amount with exchange rate passed in argument.
     *
     * @param rateExchange exchange rate
     * @param amount       Transaction to convert
     * @return Transaction amount converted with new exchange rate
     */
    public BigDecimal convertAmount(RateExchange rateExchange, BigDecimal amount) {
        Objects.requireNonNull(amount, "Transaction amount should not be null");
        Objects.requireNonNull(rateExchange, "Rate Exchange should not be null");
        Objects.requireNonNull(rateExchange.getRate(), "Rate Exchange amount should not be null");

        return amount.multiply(rateExchange.getRate());
    }

    /**
     * Sets an {@link RateExchangeRepository} instance.
     *
     * @param repository RateExchange repository instance
     */
    public void setRepository(RateExchangeRepository repository) {
        this.repository = repository;
    }
}
