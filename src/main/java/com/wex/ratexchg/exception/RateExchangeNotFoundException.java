package com.wex.ratexchg.exception;

import com.wex.ratexchg.model.RateExchange;
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
    public RateExchangeNotFoundException(String country) {
        super("No rateExchange found in database for country:" + country);
    }

    public RateExchangeNotFoundException() {
        super("No rate exchange found in database.");
    }
}
