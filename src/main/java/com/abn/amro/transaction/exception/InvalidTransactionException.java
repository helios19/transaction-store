package com.abn.amro.transaction.exception;

import com.abn.amro.transaction.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an {@link com.abn.amro.transaction.model.Transaction} instance contains invalid field values
 * (e.g {@link com.abn.amro.transaction.model.Transaction#acctNum} is null, invalid amount, etc.)
 *
 * @see com.abn.amro.transaction.model.Transaction
 * @see HttpStatus
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(Transaction transaction) {
        super("Invalid Transaction field values [" + transaction + "]");
    }
}
