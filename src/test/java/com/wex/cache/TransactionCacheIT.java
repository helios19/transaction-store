package com.wex.cache;

import com.wex.common.utils.ClassUtils;
import com.wex.transaction.model.Transaction;
import com.wex.transaction.repository.TransactionRepository;
import com.wex.transaction.service.TransactionServiceImpl;
import com.google.common.collect.Lists;
import com.wex.common.ClassTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles({"test"})
@SpringBootTest(classes = TransactionCacheIT.TestCacheConfig.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionCacheIT {
    @LocalServerPort
    private int port;

    @Autowired
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @MockBean
    private TransactionRepository repository;

    @Autowired
    private CacheManager cacheManager;

    private Transaction sampleTransaction = ClassTestUtils.TRANSACTION_SAMPLE;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        transactionService.save(sampleTransaction);
        transactionService.setRepository(repository);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // reset transaction cache
        cacheManager.getCache(ClassUtils.TRANSACTIONS_COLLECTION_NAME).clear();
    }

    @Test
    @DirtiesContext
    public void shouldFindTransactionByIdFromCache() {

        // given
        when(repository.findById(any(Long.class)))
                .thenReturn(Optional.of(sampleTransaction));

        Long id = 1L;
        Cache transactionCache = cacheManager.getCache(ClassUtils.TRANSACTIONS_COLLECTION_NAME);
        Cache.ValueWrapper beforeFillingCache = transactionCache.get(id);


        // when
        Optional<Transaction> transactionFromRepo = transactionService.findById(id);
        Optional<Transaction> transactionFromCache = transactionService.findById(id);

        // then
        Cache.ValueWrapper afterFillingCache = transactionCache.get(id);
        assertNull(beforeFillingCache);
        assertNotNull(afterFillingCache);
        assertTrue(transactionFromRepo.isPresent());
        assertTrue(transactionFromCache.isPresent());
        assertEquals(transactionFromCache, transactionFromRepo);

        verify(repository, times(1)).findById(any(Long.class));
    }

    @Test
    @DirtiesContext
    public void shouldFindAllTransactionsFromCache() {

        // given
        when(repository.findAll()).thenReturn(
                Lists.newArrayList(sampleTransaction));

        // when
        List<Transaction> transactionsFromRepo = transactionService.findAll();
        List<Transaction> transactionsFromCache = transactionService.findAll();

        // then
        assertFalse(transactionsFromRepo.isEmpty());
        assertFalse(transactionsFromCache.isEmpty());
        assertEquals(transactionsFromCache, transactionsFromRepo);

        verify(repository, times(1)).findAll();

    }

    @Configuration
    @ComponentScan({"com.wex.transaction.service", "com.wex.transaction.model"})
    @EnableAutoConfiguration
    @EnableCaching
    public static class TestCacheConfig {
        @Bean
        CacheManager cacheManager() {
            return new ConcurrentMapCacheManager(ClassUtils.TRANSACTIONS_COLLECTION_NAME);
        }
    }

}
