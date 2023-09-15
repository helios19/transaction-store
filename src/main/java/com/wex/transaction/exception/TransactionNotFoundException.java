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
    public TransactionNotFoundException(String customerId) {
        super("No transaction found in database for customer id:" + customerId);
    }

    public TransactionNotFoundException() {
        super("No transaction found in database.");
    }
}
