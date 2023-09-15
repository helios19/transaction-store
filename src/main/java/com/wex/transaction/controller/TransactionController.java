package com.wex.transaction.controller;

import com.wex.common.utils.ClassUtils;
import com.wex.transaction.dto.TransactionSummary;
import com.wex.transaction.exception.RateExchangeNotFoundException;
import com.wex.transaction.exception.TransactionNotFoundException;
import com.wex.transaction.model.RateExchange;
import com.wex.transaction.model.Transaction;
import com.wex.transaction.service.CsvService;
import com.wex.transaction.service.RateExchangeService;
import com.wex.transaction.service.TransactionService;
import com.wex.transaction.service.TransactionSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Transaction controller class defining the HTTP operations available for the {@link Transaction} resource. This controller
 * is mainly used to return transaction summary reports.
 *
 * @see Transaction
 * @see TransactionSummary
 * @see TransactionSummaryService
 * @see RestController
 */
@RestController
@RequestMapping("/transaction-summary")
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    private final RateExchangeService rateExchangeService;

    private final TransactionSummaryService transactionSummaryService;
    private final CsvService csvService;

    @Autowired
    public TransactionController(TransactionService transactionService,
                                 TransactionSummaryService transactionSummaryService,
                                 RateExchangeService rateExchangeService,
                                 CsvService csvService) {
        this.transactionService = transactionService;
        this.rateExchangeService = rateExchangeService;
        this.transactionSummaryService = transactionSummaryService;
        this.csvService = csvService;
    }

    // post and save a new transaction
    /**
     * Returns the total transaction summary report.
     *
     * @return The whole transaction summary report
     * @throws TransactionNotFoundException if no transaction found in database
     */
    @RequestMapping(value = "/all", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionSummary> saveTransaction() {

        List<Transaction> transactions = transactionService.findAll();

        if (CollectionUtils.isEmpty(transactions)) {
            throw new TransactionNotFoundException();
        }

        return ResponseEntity
                .ok()
                .body(transactionSummaryService.buildTransactionSummaryReport(transactions));
    }

    /**
     * Returns the total transaction summary report.
     *
     * @return The whole transaction summary report
     * @throws TransactionNotFoundException if no transaction found in database
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionSummary>> getAllTransactionSummaryReport() {

        List<Transaction> transactions = transactionService.findAll();

        if (CollectionUtils.isEmpty(transactions)) {
            throw new TransactionNotFoundException();
        }

        return ResponseEntity
                .ok()
                .body(transactionSummaryService.buildTransactionSummaryReport(transactions));
    }

    /**
     * Returns the total transaction summary report.
     *
     * @return The whole transaction summary report
     * @throws TransactionNotFoundException if no transaction found in database
     */
    @RequestMapping(value = "/transaction/{transaction_id}/{currency}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionSummary> getConvertedTransactionAmount(@PathVariable String currency, @PathVariable Integer transactionId) {

        Transaction transaction = transactionService.findById(Long.valueOf(transactionId))
                .orElseThrow(TransactionNotFoundException::new);

        RateExchange rateExchange = rateExchangeService.findById(Long.valueOf(currency))
                .orElseThrow(RateExchangeNotFoundException::new);


        // calculate new amount based on currency passed in parameter

        if (CollectionUtils.isEmpty(transactions)) {
            throw new TransactionNotFoundException();
        }

        return ResponseEntity
                .ok()
                .body(transactionSummaryService.buildTransactionSummaryReport(transactions));
    }

    /**
     * Returns the csv export file of the transaction summary report.
     *
     * @return Transaction summary for the given client
     * @throws TransactionNotFoundException if no transaction found in database
     */
    @GetMapping(value = "/export-csv", produces = ClassUtils.MEDIATYPE_TEXT_HTML)
    public ResponseEntity<Resource> getAllTransactionSummaryReportInCsv() {

        List<Transaction> transactions = transactionService.findAll();

        if (CollectionUtils.isEmpty(transactions)) {
            throw new TransactionNotFoundException();
        }

        InputStreamResource resource = new InputStreamResource(
                csvService.loadInputStream(
                        transactionSummaryService.buildTransactionSummaryReport(transactions)));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + ClassUtils.CSV_FILENAME)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }

}
