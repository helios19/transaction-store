package com.abn.amro.transaction.controller;

import com.abn.amro.transaction.dto.TransactionSummary;
import com.abn.amro.transaction.exception.TransactionNotFoundException;
import com.abn.amro.transaction.model.Transaction;
import com.abn.amro.transaction.service.CsvService;
import com.abn.amro.transaction.service.TransactionService;
import com.abn.amro.transaction.service.TransactionSummaryService;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static com.abn.amro.common.utils.ClassUtils.HUNDRED;
import static com.abn.amro.common.utils.ClassUtils.TEN_MILLIONS;
import static com.abn.amro.common.utils.ClassUtils.toDate;
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
public class TransactionControllerMockTest {
    @Mock
    private TransactionService transactionService;

    @Spy
    private TransactionSummaryService transactionSummaryService;

    @Spy
    private CsvService csvService;

    @InjectMocks
    TransactionController controller;

    private TransactionSummary transactionSummary = TransactionSummary
            .builder()
            .clientInformation("CL  432100020001")
            .productInformation("SGX FUNK    20100910")
            .date(toDate("20100910"))
            .totalTransactionAmount(0)
            .build();

    private Transaction transaction = Transaction
            .builder()
            .recordCode("315")
            .clientType("CL  ")
            .clientNum("4321")
            .acctNum("0002")
            .subAcctNum("0001")
            .oppositePartyCode("SGXDC ")
            .productGroupCode("FU")
            .exchangeCode("SGX ")
            .symbol("NK    ")
            .expirationDate("20100910")
            .currencyCode("JPY")
            .movementCode("01")
            .buySellCode("B")
            .qttyLongSign(" ")
            .qttyLong(new BigInteger("0000000001"))
            .qttyShortSign(" ")
            .qttyShort(new BigInteger("0000000000"))
            .exchBrokerFeeDec(new BigDecimal("000000000060").divide(HUNDRED))
            .exchBrokerFeeDc("D")
            .exchBrokerFeeCurCode("USD")
            .clearingFeeDec(new BigDecimal("000000000030").divide(HUNDRED))
            .clearingFeeDc("D")
            .clearingFeeCurCode("USD")
            .commission(new BigDecimal("000000000000").divide(HUNDRED))
            .commissionDc("D")
            .commissionCurCode("JPY")
            .transactionDate(toDate("20100820"))
            .futureReference("001238")
            .ticketNumber("0     ")
            .externalNumber("688032")
            .transactionPriceDec(new BigDecimal("000092500000000").divide(TEN_MILLIONS))
            .traderInitials("      ")
            .oppositeTraderId("       ")
            .openCloseCode(" ")
            .filler("O                                                                                                                              ")
            .build();

    @Test
    public void shouldReturnAllTransactionSummaries() {

        // Given
        when(transactionService.findAll())
                .thenReturn(Lists.newArrayList(transaction));

        when(transactionSummaryService.buildTransactionSummaryReport(any(List.class)))
                .thenReturn(Lists.newArrayList(transactionSummary));

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
                .thenReturn(Lists.newArrayList(transaction));

        when(transactionSummaryService.buildTransactionSummaryReport(any(List.class)))
                .thenReturn(Lists.newArrayList(transactionSummary));

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
