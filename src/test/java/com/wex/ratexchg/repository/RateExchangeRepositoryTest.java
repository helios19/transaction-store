package com.wex.ratexchg.repository;

import com.wex.common.utils.ClassUtils;
import com.wex.ratexchg.model.RateExchange;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static com.wex.common.ClassTestUtils.RATEEXCHANGE_SAMPLE;
import static com.wex.common.ClassTestUtils.RATEEXCHANGE_SAMPLE_2;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RateExchangeRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    RateExchangeRepository repository;

    @BeforeEach
    public void setUp() throws Exception {
        repository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }
    @Test
    public void shouldNotFindAnyRateExchangeIfRepositoryIsEmpty() {
        Iterable transactions = repository.findAll();
        assertThat(transactions).isEmpty();
    }

    @Test
    public void shouldSaveRateExchange() {
        RateExchange transaction = repository.save(RATEEXCHANGE_SAMPLE);
        assertThat(transaction).hasFieldOrPropertyWithValue("description", "Australia-Dollar");
        assertThat(transaction).hasFieldOrPropertyWithValue("country", "Australia");
        assertThat(transaction).hasFieldOrPropertyWithValue("currency", "Dollar");
        assertThat(transaction).hasFieldOrPropertyWithValue("effectiveDate", ClassUtils.toDate("2023-03-31"));
        assertThat(transaction).hasFieldOrPropertyWithValue("rate", new BigDecimal(1.4939999999999999946709294817992486059665679931640625));
    }

    @Test
    public void shouldFindAllRateExchanges() {
        RateExchange t1 = RATEEXCHANGE_SAMPLE;
        t1 = repository.save(t1);
        RateExchange t2 = RATEEXCHANGE_SAMPLE_2;
        t2 = repository.save(t2);
        Iterable transactions = repository.findAll();
        assertThat(transactions).hasSize(2).contains(t1, t2);
    }
}
