package com.wex.transaction.exception;

import com.wex.transaction.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when no {@link Transaction} resource cannot be found.
 *
 * @see Transaction
 * @see HttpStatus
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String transactionId) {
        super("No transaction found in database for transaction id:" + transactionId);
    }

    public TransactionNotFoundException() {
        super("No transaction found in database.");
    }
}
