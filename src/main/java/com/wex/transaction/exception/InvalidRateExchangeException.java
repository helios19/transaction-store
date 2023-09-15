package com.wex.transaction.exception;

import com.wex.transaction.model.RateExchange;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an {@link RateExchange} instance contains invalid field values
 * (e.g {@link RateExchange#getRate()} is null, invalid amount, etc.)
 *
 * @see RateExchange
 * @see HttpStatus
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRateExchangeException extends RuntimeException {
    public InvalidRateExchangeException(RateExchange rateExchange) {
        super("Invalid RateExchange field values [" + rateExchange + "]");
    }
}
