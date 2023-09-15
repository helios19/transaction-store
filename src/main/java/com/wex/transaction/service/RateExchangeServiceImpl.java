package com.wex.transaction.service;

import com.wex.common.utils.ClassUtils;
import com.wex.transaction.model.RateExchange;
import com.wex.transaction.repository.RateExchangeRepository;
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

/**
 * Service class providing CRUD operations and caching logic for {@link RateExchange} resource.
 *
 * @see RateExchangeRepository
 */
@Service
@Slf4j
@CacheConfig(cacheNames = ClassUtils.RATE_EXCHANGE_COLLECTION_NAME)
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
     * Sets an {@link RateExchangeRepository} instance.
     *
     * @param repository RateExchange repository instance
     */
    public void setRepository(RateExchangeRepository repository) {
        this.repository = repository;
    }
}
