package com.wex.ratexchg.handler;

import com.wex.common.handler.GlobalExceptionHandler;
import com.wex.ratexchg.exception.InvalidRateExchangeException;
import com.wex.ratexchg.exception.RateExchangeNotFoundException;
import com.wex.ratexchg.model.RateExchange;
import com.wex.transaction.exception.InvalidParameterException;
import com.wex.transaction.exception.InvalidTransactionException;
import com.wex.transaction.exception.TransactionNotFoundException;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception handler class used for catching any {@link com.wex.ratexchg.model.RateExchange} related exceptions
 * and transforming them into HATEOAS JSON message.
 *
 * @see VndErrors
 * @see RateExchange
 * @see InvalidTransactionException
 * @see InvalidParameterException
 * @see TransactionNotFoundException
 */
@ControllerAdvice
public class RateExchangeExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(InvalidRateExchangeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors handleInvalidRateExchangeException(InvalidRateExchangeException ex) {
        return getVndErrors(ex);
    }

    @ResponseBody
    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    VndErrors handleInvalidParameterException(InvalidParameterException ex) {
        return getVndErrors(ex);
    }

    @ResponseBody
    @ExceptionHandler(RateExchangeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors handleRateExchangeNotFoundException(RateExchangeNotFoundException ex) {
        return getVndErrors(ex);
    }

}
