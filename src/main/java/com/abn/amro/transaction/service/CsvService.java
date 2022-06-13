package com.abn.amro.transaction.service;

import com.abn.amro.transaction.dto.TransactionSummary;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Csv service interface.
 */
public interface CsvService {
    public ByteArrayInputStream loadInputStream(final List<TransactionSummary> transactionSummaries);
}
