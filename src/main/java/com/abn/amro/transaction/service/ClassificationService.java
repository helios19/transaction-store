package com.abn.amro.transaction.service;

import com.abn.amro.transaction.model.Transaction;

import java.util.List;

/**
 * Classification service interface.
 */
public interface ClassificationService {

    List<ClassificationEnum> classifyCustomer(List<Transaction> transactions);
}
