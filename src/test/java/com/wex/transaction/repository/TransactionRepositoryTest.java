package com.wex.transaction.repository;

import com.wex.common.utils.ClassUtils;
import com.wex.transaction.model.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static com.wex.common.ClassTestUtils.TRANSACTION_SAMPLE;
import static com.wex.common.ClassTestUtils.TRANSACTION_SAMPLE_2;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TransactionRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    TransactionRepository repository;

    @BeforeEach
    public void setUp() throws Exception {
        repository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }
    @Test
    public void shouldNotFindAnyTransactionIfRepositoryIsEmpty() {
        Iterable transactions = repository.findAll();
        assertThat(transactions).isEmpty();
    }

    @Test
    public void shouldSaveTransaction() {
        Transaction transaction = repository.save(TRANSACTION_SAMPLE);
        assertThat(transaction).hasFieldOrPropertyWithValue("description", "transaction 1");
        assertThat(transaction).hasFieldOrPropertyWithValue("country", "United States");
        assertThat(transaction).hasFieldOrPropertyWithValue("transactionDate", ClassUtils.toDate("2023-09-10"));
        assertThat(transaction).hasFieldOrPropertyWithValue("amount", new BigDecimal("000092500000000").divide(ClassUtils.TEN_MILLIONS));
    }

    @Test
    public void shouldFindAllTransactions() {
        Transaction t1 = TRANSACTION_SAMPLE;
        t1 = repository.save(t1);
        Transaction t2 = TRANSACTION_SAMPLE_2;
        t2 = repository.save(t2);
        Iterable transactions = repository.findAll();
        assertThat(transactions).hasSize(2).contains(t1, t2);
    }
}
