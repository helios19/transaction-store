package com.abn.amro.transaction.controller;

import com.abn.amro.transaction.dto.TransactionSummary;
import com.abn.amro.transaction.exception.TransactionNotFoundException;
import com.abn.amro.transaction.model.Transaction;
import com.abn.amro.transaction.service.CsvService;
import com.abn.amro.transaction.service.TransactionService;
import com.abn.amro.transaction.service.TransactionSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.abn.amro.common.utils.ClassUtils.CSV_FILENAME;
import static com.abn.amro.common.utils.ClassUtils.MEDIATYPE_TEXT_HTML;

/**
 * Transaction controller class defining the HTTP operations available for the {@link Transaction} resource. This controller
 * is mainly used to return a customer transaction summary report.
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

    private final TransactionSummaryService transactionSummaryService;
    private final CsvService csvService;

    @Autowired
    public TransactionController(TransactionService transactionService,
                                 TransactionSummaryService transactionSummaryService,
                                 CsvService csvService) {
        this.transactionService = transactionService;
        this.transactionSummaryService = transactionSummaryService;
        this.csvService = csvService;
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
     * Returns the csv export file of the transaction summary report.
     *
     * @return Transaction summary for the given client
     * @throws TransactionNotFoundException if no transaction found in database
     */
    @GetMapping(value = "/export-csv", produces = MEDIATYPE_TEXT_HTML)
    public ResponseEntity<Resource> getAllTransactionSummaryReportInCsv() {

        List<Transaction> transactions = transactionService.findAll();

        if (CollectionUtils.isEmpty(transactions)) {
            throw new TransactionNotFoundException();
        }

        InputStreamResource resource = new InputStreamResource(
                csvService.loadInputStream(
                        transactionSummaryService.buildTransactionSummaryReport(transactions)));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + CSV_FILENAME)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }

}
