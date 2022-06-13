package com.abn.amro.transaction.service;

import com.abn.amro.transaction.dto.TransactionSummary;
import com.abn.amro.transaction.model.Transaction;

import java.util.List;

/**
 * Transaction Summary service interface.
 */
public interface TransactionSummaryService {
    List<TransactionSummary> buildTransactionSummaryReport(List<Transaction> transactions);
}
