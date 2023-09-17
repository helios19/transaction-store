package com.wex.transaction.exception;

import com.wex.transaction.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an {@link Transaction} instance contains invalid field values
 * (e.g {@link Transaction#id} is null, invalid amount, etc.)
 *
 * @see Transaction
 * @see HttpStatus
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(Transaction transaction) {
        super("Invalid Transaction field values [" + transaction + "]");
    }
}
