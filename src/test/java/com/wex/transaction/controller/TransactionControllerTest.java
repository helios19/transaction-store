package com.wex.transaction.controller;

import com.google.common.collect.Lists;
import com.wex.ratexchg.service.RateExchangeService;
import com.wex.ratexchg.service.RateExchangeServiceImpl;
import com.wex.transaction.dto.TransactionDto;
import com.wex.transaction.exception.TransactionNotFoundException;
import com.wex.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.wex.common.ClassTestUtils.RATEEXCHANGE_SAMPLE;
import static com.wex.common.ClassTestUtils.TRANSACTION_SAMPLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
    @Mock
    private TransactionService transactionService;
    @Spy
    private RateExchangeService rateExchangeService = Mockito.spy(RateExchangeServiceImpl.class);

    @InjectMocks
    TransactionController controller;

    @Test
    public void shouldReturnAllTransactions() {

        // Given
        when(transactionService.findAll())
                .thenReturn(Lists.newArrayList(TRANSACTION_SAMPLE));

        // When
        ResponseEntity<List<TransactionDto>> response = controller.getAllTransactions();

        // Then
        assertThat(response).isNotNull();
        verify(transactionService, times(1)).findAll();
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void shouldReturnTransactionAmountConverted() {

        // Given
        when(transactionService.findById(any()))
                .thenReturn(Optional.of(TRANSACTION_SAMPLE));

        doReturn(Optional.of(RATEEXCHANGE_SAMPLE)).when(rateExchangeService).findByCountry(any());

        // When
        ResponseEntity<TransactionDto> transactionConverted = controller.convertTransactionAmount(1, "Australia");

        System.out.println("transactionConverted.getBody() : " + transactionConverted.getBody());

        // Then
        assertThat(transactionConverted).isNotNull();
        assertThat(transactionConverted.getBody().getDescription()).isEqualTo("transaction 1");
        assertThat(transactionConverted.getBody().getTransactionDate()).isEqualTo("2010-09-10");
        assertThat(transactionConverted.getBody().getCountry()).isEqualTo("Australia");
        assertThat(transactionConverted.getBody().getAmount()).isEqualTo("13819.50");
        verify(transactionService, times(1)).findById(any());
        verifyNoMoreInteractions(transactionService);
        verify(rateExchangeService, times(1)).findByCountry(any());
        verify(rateExchangeService, times(1)).convertAmount(any(), any());
        verifyNoMoreInteractions(rateExchangeService);

    }

    @Test
    public void shouldThrowTransactionNotFoundExceptionWhenNoTransactionExists() {
        // Given
        when(transactionService.findAll())
                .thenReturn(Lists.newArrayList());

        // When
        TransactionNotFoundException thrown = assertThrows(
                TransactionNotFoundException.class,
                () -> controller.getAllTransactions(),
                "Expected getAllTransactionSummaryReport() to throw, but it didn't"
        );

        // Then
        assertTrue(thrown.getMessage().contains("No transaction found in database."));

        // When
        thrown = assertThrows(
                TransactionNotFoundException.class,
                () -> controller.getAllTransactions(),
                "Expected getAllTransactionSummaryReport() to throw, but it didn't"
        );

        // Then
        assertTrue(thrown.getMessage().contains("No transaction found in database."));
    }
}
