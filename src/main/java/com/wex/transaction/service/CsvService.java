package com.wex.transaction.service;

import com.wex.transaction.dto.TransactionSummary;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Csv service interface.
 */
public interface CsvService {
    public ByteArrayInputStream loadInputStream(final List<TransactionSummary> transactionSummaries);
}
