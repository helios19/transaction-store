package com.wex.ratexchg.controller;

import com.wex.common.utils.ClassUtils;
import com.wex.ratexchg.dto.RateExchangeDto;
import com.wex.ratexchg.exception.RateExchangeNotFoundException;
import com.wex.ratexchg.model.RateExchange;
import com.wex.ratexchg.service.RateExchangeService;
import com.wex.transaction.exception.TransactionNotFoundException;
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

import static com.wex.common.utils.ClassUtils.DEFAULT_RATE_EXCHANGE;

/**
 * Rate exchange controller class defining the HTTP operations available for the {@link RateExchange} resource. This controller
 * is mainly used to return the existing list of rate exchanges.
 *
 * @see RateExchange
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
            throw new RateExchangeNotFoundException();
        }

        return ResponseEntity
                .ok()
                .body(rateExchanges.stream()
                        .map(ClassUtils::convertToDto)
                        .collect(Collectors.toList()));
    }

    /**
     * Returns the total transaction summary report.
     *
     * @return The whole transaction summary report
     * @throws TransactionNotFoundException if no transaction found in database
     */
    @RequestMapping(value = "/countries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getRateExchangeCountries() {
        log.info("loading rate exchange countries");

        List<RateExchange> rateExchanges = rateExchangeService.findAll();

        if (CollectionUtils.isEmpty(rateExchanges)) {
            throw new RateExchangeNotFoundException();
        }

        // add default US rate exchange
        rateExchanges.add(DEFAULT_RATE_EXCHANGE);

        return ResponseEntity
                .ok()
                .body(rateExchanges.stream()
                        .map(rateExchange -> rateExchange.getCountry())
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList()));
    }

}
