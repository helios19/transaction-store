package com.wex.transaction.service;

import com.wex.transaction.model.Transaction;
import com.wex.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.wex.common.ClassTestUtils.TRANSACTION_SAMPLE;
import static com.wex.common.ClassTestUtils.TRANSACTION_SAMPLE_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction1;
    private Transaction transaction2;

    @BeforeEach
    public void setup() {
        transaction1 = TRANSACTION_SAMPLE;
        transaction2 = TRANSACTION_SAMPLE_2;
    }

    @Test
    public void givenTransactionWhenSavedThenReturnTransactionObject() {
        // Given
        given(transactionRepository.save(transaction1)).willReturn(transaction1);

        // When
        Transaction t = transactionService.save(transaction1);

        // Then
        assertThat(t).isNotNull();
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verifyNoMoreInteractions(transactionRepository);
    }

    @Test
    public void givenListOfTransactionsWhenFindAllInvokedThenReturnTransactionList() {
        // Given
        given(transactionRepository.findAll()).willReturn(List.of(transaction1, transaction2));

        // When
        List<Transaction> transactionList = transactionService.findAll();

        // Then
        assertThat(transactionList).isNotNull();
        assertThat(transactionList.size()).isEqualTo(2);
        verify(transactionRepository, times(1)).findAll();
        verifyNoMoreInteractions(transactionRepository);
    }
}
