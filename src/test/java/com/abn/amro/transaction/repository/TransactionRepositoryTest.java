package com.abn.amro.transaction.repository;

import com.abn.amro.transaction.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.abn.amro.common.ClassTestUtils.TRANSACTION_SAMPLE;
import static com.abn.amro.common.ClassTestUtils.TRANSACTION_SAMPLE_2;
import static com.abn.amro.common.utils.ClassUtils.HUNDRED;
import static com.abn.amro.common.utils.ClassUtils.TEN_MILLIONS;
import static com.abn.amro.common.utils.ClassUtils.toDate;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TransactionRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    TransactionRepository repository;

    @Test
    public void shouldNotFindAnyTransactionIfRepositoryIsEmpty() {
        Iterable tutorials = repository.findAll();
        assertThat(tutorials).isEmpty();
    }

    @Test
    public void shouldSaveTransaction() {
        Transaction transaction = repository.save(TRANSACTION_SAMPLE);
        assertThat(transaction).hasFieldOrPropertyWithValue("recordCode", "315");
        assertThat(transaction).hasFieldOrPropertyWithValue("clientNum", "4321");
        assertThat(transaction).hasFieldOrPropertyWithValue("acctNum", "0002");
        assertThat(transaction).hasFieldOrPropertyWithValue("subAcctNum", "0001");
        assertThat(transaction).hasFieldOrPropertyWithValue("oppositePartyCode", "SGXDC ");
        assertThat(transaction).hasFieldOrPropertyWithValue("productGroupCode", "FU");
        assertThat(transaction).hasFieldOrPropertyWithValue("exchangeCode", "SGX ");
        assertThat(transaction).hasFieldOrPropertyWithValue("symbol", "NK    ");
        assertThat(transaction).hasFieldOrPropertyWithValue("expirationDate", "20100910");
        assertThat(transaction).hasFieldOrPropertyWithValue("currencyCode", "JPY");
        assertThat(transaction).hasFieldOrPropertyWithValue("movementCode", "01");
        assertThat(transaction).hasFieldOrPropertyWithValue("buySellCode", "B");
        assertThat(transaction).hasFieldOrPropertyWithValue("qttyLongSign", " ");
        assertThat(transaction).hasFieldOrPropertyWithValue("qttyLong", new BigInteger("0000000001"));
        assertThat(transaction).hasFieldOrPropertyWithValue("qttyShortSign", " ");
        assertThat(transaction).hasFieldOrPropertyWithValue("qttyShort", new BigInteger("0000000000"));
        assertThat(transaction).hasFieldOrPropertyWithValue("exchBrokerFeeDec", new BigDecimal("000000000060").divide(HUNDRED));
        assertThat(transaction).hasFieldOrPropertyWithValue("exchBrokerFeeDc", "D");
        assertThat(transaction).hasFieldOrPropertyWithValue("exchBrokerFeeCurCode", "USD");
        assertThat(transaction).hasFieldOrPropertyWithValue("clearingFeeDec", new BigDecimal("000000000030").divide(HUNDRED));
        assertThat(transaction).hasFieldOrPropertyWithValue("clearingFeeDc", "D");
        assertThat(transaction).hasFieldOrPropertyWithValue("clearingFeeCurCode", "USD");
        assertThat(transaction).hasFieldOrPropertyWithValue("commission", new BigDecimal("000000000000").divide(HUNDRED));
        assertThat(transaction).hasFieldOrPropertyWithValue("commissionDc", "D");
        assertThat(transaction).hasFieldOrPropertyWithValue("commissionCurCode", "JPY");
        assertThat(transaction).hasFieldOrPropertyWithValue("transactionDate", toDate("20100820"));
        assertThat(transaction).hasFieldOrPropertyWithValue("futureReference", "001238");
        assertThat(transaction).hasFieldOrPropertyWithValue("ticketNumber", "0     ");
        assertThat(transaction).hasFieldOrPropertyWithValue("externalNumber", "688032");
        assertThat(transaction).hasFieldOrPropertyWithValue("transactionPriceDec", new BigDecimal("000092500000000").divide(TEN_MILLIONS));
        assertThat(transaction).hasFieldOrPropertyWithValue("traderInitials", "      ");
        assertThat(transaction).hasFieldOrPropertyWithValue("oppositeTraderId", "       ");
        assertThat(transaction).hasFieldOrPropertyWithValue("openCloseCode", " ");
        assertThat(transaction).hasFieldOrPropertyWithValue("filler", "O                                                                                                                              ");
    }

    @Test
    public void shouldFindAllTransactions() {
        Transaction t1 = TRANSACTION_SAMPLE;
        entityManager.persist(t1);
        Transaction t2 = TRANSACTION_SAMPLE_2;
        entityManager.persist(t2);
        Iterable transactions = repository.findAll();
        assertThat(transactions).hasSize(2).contains(t1, t2);
    }
}
