package com.wex.transaction.exception;

import com.wex.transaction.model.RateExchange;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when no {@link RateExchange} resource cannot be found.
 *
 * @see RateExchange
 * @see HttpStatus
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RateExchangeNotFoundException extends RuntimeException {
    public RateExchangeNotFoundException(String customerId) {
        super("No rateExchange found in database for customer id:" + customerId);
    }

    public RateExchangeNotFoundException() {
        super("No rateExchange found in database.");
    }
}
