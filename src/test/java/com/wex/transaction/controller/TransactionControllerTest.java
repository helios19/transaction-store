package com.wex.transaction.controller;

import com.wex.transaction.dto.TransactionSummary;
import com.wex.transaction.exception.TransactionNotFoundException;
import com.wex.transaction.service.CsvService;
import com.wex.transaction.service.TransactionService;
import com.wex.transaction.service.TransactionSummaryService;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayInputStream;
import java.util.List;

import static com.wex.common.ClassTestUtils.TRANSACTION_SAMPLE;
import static com.wex.common.ClassTestUtils.TRANSACTION_SUMMARY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
    private TransactionSummaryService transactionSummaryService;

    @Spy
    private CsvService csvService;

    @InjectMocks
    TransactionController controller;

    @Test
    public void shouldReturnAllTransactionSummaries() {

        // Given
        when(transactionService.findAll())
                .thenReturn(Lists.newArrayList(TRANSACTION_SAMPLE));

        when(transactionSummaryService.buildTransactionSummaryReport(any(List.class)))
                .thenReturn(Lists.newArrayList(TRANSACTION_SUMMARY));

        // When
        ResponseEntity<List<TransactionSummary>> response = controller.getAllTransactionSummaryReport();

        // Then
        assertThat(response).isNotNull();
        verify(transactionService, times(1)).findAll();
        verifyNoMoreInteractions(transactionService);
        verify(transactionSummaryService, times(1)).buildTransactionSummaryReport(any(List.class));
        verifyNoMoreInteractions(transactionSummaryService);
    }

    @Test
    public void shouldReturnAllTransactionSummariesInCsv() {

        // Given
        when(transactionService.findAll())
                .thenReturn(Lists.newArrayList(TRANSACTION_SAMPLE));

        when(transactionSummaryService.buildTransactionSummaryReport(any(List.class)))
                .thenReturn(Lists.newArrayList(TRANSACTION_SUMMARY));

        when(csvService.loadInputStream(any()))
                .thenReturn(new ByteArrayInputStream("".getBytes()));

        // When
        ResponseEntity<Resource> response = controller.getAllTransactionSummaryReportInCsv();

        // Then
        assertThat(response).isNotNull();
        verify(transactionService, times(1)).findAll();
        verifyNoMoreInteractions(transactionService);
        verify(transactionSummaryService, times(1)).buildTransactionSummaryReport(any(List.class));
        verifyNoMoreInteractions(transactionSummaryService);
        verify(csvService, times(1)).loadInputStream(any(List.class));
        verifyNoMoreInteractions(csvService);

    }

    @Test
    public void shouldThrowTransactionNotFoundExceptionWhenNoTransactionExists() {
        // Given
        when(transactionService.findAll())
                .thenReturn(Lists.newArrayList());

        // When
        TransactionNotFoundException thrown = assertThrows(
                TransactionNotFoundException.class,
                () -> controller.getAllTransactionSummaryReport(),
                "Expected getAllTransactionSummaryReport() to throw, but it didn't"
        );

        // Then
        assertTrue(thrown.getMessage().contains("No transaction found in database."));

        // When
        thrown = assertThrows(
                TransactionNotFoundException.class,
                () -> controller.getAllTransactionSummaryReportInCsv(),
                "Expected getAllTransactionSummaryReport() to throw, but it didn't"
        );

        // Then
        assertTrue(thrown.getMessage().contains("No transaction found in database."));
    }
}
