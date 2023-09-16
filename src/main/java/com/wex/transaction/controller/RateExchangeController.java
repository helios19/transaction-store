package com.wex.transaction.controller;

import com.wex.common.utils.ClassUtils;
import com.wex.transaction.dto.RateExchangeDto;
import com.wex.transaction.dto.TransactionSummary;
import com.wex.transaction.exception.TransactionNotFoundException;
import com.wex.transaction.model.RateExchange;
import com.wex.transaction.model.Transaction;
import com.wex.transaction.service.RateExchangeService;
import com.wex.transaction.service.TransactionSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Rate exchange controller class defining the HTTP operations available for the {@link RateExchange} resource. This controller
 * is mainly used to return the existing list of rate exchanges.
 *
 * @see Transaction
 * @see TransactionSummary
 * @see TransactionSummaryService
 * @see RestController
 */
@RestController
@RequestMapping("/rate-exchanges")
@Slf4j
public class RateExchangeController {

    private final RateExchangeService rateExchangeService;


    @Autowired
    public RateExchangeController(RateExchangeService rateExchangeService) {
        this.rateExchangeService = rateExchangeService;
    }

    /**
     * Returns the total transaction summary report.
     *
     * @return The whole transaction summary report
     * @throws TransactionNotFoundException if no transaction found in database
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RateExchangeDto>> getRateExchanges() {

        List<RateExchange> rateExchanges = rateExchangeService.findAll();

        if (CollectionUtils.isEmpty(rateExchanges)) {
            throw new TransactionNotFoundException();
        }

        return ResponseEntity
                .ok()
                .body(rateExchanges.stream()
                        .map(ClassUtils::convertToDto)
                        .collect(Collectors.toList()));
    }

}
