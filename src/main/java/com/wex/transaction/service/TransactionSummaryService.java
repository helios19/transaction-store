package com.wex.transaction.service;

import com.wex.transaction.dto.TransactionSummary;
import com.wex.transaction.model.Transaction;

import java.util.List;

/**
 * Transaction Summary service interface.
 */
public interface TransactionSummaryService {
    List<TransactionSummary> buildTransactionSummaryReport(List<Transaction> transactions);
}
