package com.wex.transaction.controller;

import com.wex.common.utils.ClassUtils;
import com.wex.ratexchg.exception.RateExchangeNotFoundException;
import com.wex.ratexchg.model.RateExchange;
import com.wex.ratexchg.service.RateExchangeService;
import com.wex.transaction.dto.TransactionDto;
import com.wex.transaction.exception.TransactionNotFoundException;
import com.wex.transaction.model.Transaction;
import com.wex.transaction.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import static com.wex.common.utils.ClassUtils.convertToDto;

/**
 * Transaction controller class defining the HTTP operations available for the {@link Transaction} resource. This controller
 * is mainly used to return transaction summary reports.
 *
 * @see Transaction
 * @see TransactionService
 * @see RestController
 */
@RestController
@RequestMapping("/transactions")
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    private final RateExchangeService rateExchangeService;

    @Autowired
    public TransactionController(TransactionService transactionService,
                                 RateExchangeService rateExchangeService) {
        this.transactionService = transactionService;
        this.rateExchangeService = rateExchangeService;
    }

    /**
     * Save a given transaction.
     *
     * @return The whole transaction summary report
     * @throws TransactionNotFoundException if no transaction found in database
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TransactionDto> save(@RequestBody Transaction transaction) {

        if (transaction == null || transaction.getAmount() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " 'amount' is required");
        }

        return ResponseEntity
                .ok()
                .body(convertToDto(transactionService.save(transaction)));
    }

    /**
     * Returns all transactions.
     *
     * @return The list of transactions
     * @throws TransactionNotFoundException if no transaction found in database
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {

        List<Transaction> transactions = transactionService.findAll();

        if (CollectionUtils.isEmpty(transactions)) {
            throw new TransactionNotFoundException();
        }

        return ResponseEntity
                .ok()
                .body(transactions.stream()
                        .map(ClassUtils::convertToDto)
                        .collect(Collectors.toList()));
    }

    /**
     * Returns the total transaction summary report.
     *
     * @return The whole transaction summary report
     * @throws TransactionNotFoundException if no transaction found in database
     */
    @RequestMapping(value = "/{transactionId}/{country}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Validated
    public ResponseEntity<TransactionDto> convertTransactionAmount(@PathVariable @Min(0) Integer transactionId,
                                                                   @PathVariable @NotBlank String country) {

        Transaction transaction = transactionService.findById(Long.valueOf(transactionId))
                .orElseThrow(TransactionNotFoundException::new);

        RateExchange rateExchange = rateExchangeService.findByCountry(country)
                .orElseThrow(RateExchangeNotFoundException::new);

        TransactionDto transactionDto = convertToDto(transaction);
        transactionDto.setCountry(country);
        transactionDto.setAmount(rateExchangeService
                .convertAmount(rateExchange, transaction.getAmount()) .setScale(2, RoundingMode.CEILING).toString());

        return ResponseEntity
                .ok()
                .body(transactionDto);
    }

}
