package com.abn.amro.transaction.controller;

import com.abn.amro.transaction.dto.TransactionSummary;
import com.abn.amro.transaction.exception.TransactionNotFoundException;
import com.abn.amro.transaction.model.Transaction;
import com.abn.amro.transaction.service.TransactionService;
import com.abn.amro.transaction.service.TransactionSummaryService;
import io.vavr.control.Try;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Transaction controller class defining the HTTP operations available for the {@link Transaction} resource. This controller
 * is mainly used to return a customer transaction summary including its classification.
 *
 * @see Transaction
 * @see TransactionSummary
 * @see TransactionSummaryService
 * @see RestController
 */
@RestController
@RequestMapping("/transaction-summary")
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionSummaryService transactionSummaryService;

    @Autowired
    public TransactionController(TransactionService transactionService, TransactionSummaryService transactionSummaryService) {
        this.transactionService = transactionService;
        this.transactionSummaryService = transactionSummaryService;
    }


    /**
     * Returns the total transaction summary report.
     *
     * @return The whole transaction summary report
     * @throws TransactionNotFoundException if no transaction found in database
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionSummary>> getAllTransactionSummaryReport() {

        List<Transaction> transactions = transactionService.findAll();

        if (CollectionUtils.isEmpty(transactions)) {
            throw new TransactionNotFoundException();
        }

        return ResponseEntity
                .ok()
                .body(transactionSummaryService.buildTransactionSummaryReport(transactions));
    }

    @GetMapping(value = "/exportCsv", produces = "text/csv")
    public ResponseEntity<Resource> getAllTransactionSummaryReportInCsv() {

        List<Transaction> transactions = transactionService.findAll();

        if (CollectionUtils.isEmpty(transactions)) {
            throw new TransactionNotFoundException();
        }

        List<TransactionSummary> transactionSummaryList = transactionSummaryService.buildTransactionSummaryReport(transactions);

        // replace this with your header (if required)
        String[] csvHeader = {
                "Client Information", "Production Information", "Date", "Total Transaction Amount"
        };

        ByteArrayInputStream byteArrayOutputStream;

        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                CSVPrinter csvPrinter = new CSVPrinter(
                        new PrintWriter(out),
                        CSVFormat.DEFAULT.withHeader(csvHeader))) {

            transactionSummaryList.stream().forEach(transactionSummary -> {
                Try.run(() -> csvPrinter.printRecord(
                        transactionSummary.getClientInformation(),
                        transactionSummary.getProductInformation(),
                        transactionSummary.getDate(),
                        transactionSummary.getTotalTransactionAmount()))
                        .getOrElseThrow(throwable -> new RuntimeException(throwable));
            });

            // writing the underlying stream
            csvPrinter.flush();

            byteArrayOutputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        InputStreamResource fileInputStream = new InputStreamResource(byteArrayOutputStream);

        String csvFileName = "transaction-summary-report.csv";

        // setting HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
        // defining the custom Content-Type
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity<>(
                fileInputStream,
                headers,
                HttpStatus.OK
        );
    }

    /**
     * Returns the transaction summary report of a given {@code clientInformation} and {@code date} parameters.
     *
     * @param clientInformation Client information
     * @param date      Date
     * @return Transaction summary for the given client
     * @throws TransactionNotFoundException if no transaction for the given clientInformation and date can be found
     */
    @RequestMapping(value = "/{clientInformation}/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionSummary> getTransactionSummaryReport(@PathVariable("clientInformation") String clientInformation, @PathVariable("date") String date) {

        throw new TransactionNotFoundException(clientInformation);
    }

//    /**
//     * Returns the transaction summary of a given {@code customerId} and {@code month} parameters.
//     *
//     * @param customerId Customer identifier
//     * @param month      Month
//     * @return Transaction summary for the give customer
//     * @throws TransactionNotFoundException if no transaction for the given customerId and month can be found
//     */
//    @RequestMapping(value = "/{customerId}/{month}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<TransactionSummary> findByCustomerIdMonth(@PathVariable("customerId") String customerId, @PathVariable("month") String month) {
//
//        List<Transaction> transactions = transactionService.findByCustomerId(validateCustomerId(customerId));
//        List<Transaction> monthlyTransactions = getMonthlyTransactions(transactions, validateMonth(month));
//
//        if (CollectionUtils.isEmpty(transactions) || CollectionUtils.isEmpty(monthlyTransactions)) {
//            throw new TransactionNotFoundException(customerId);
//        }
//
//        TransactionSummary transactionSummary = TransactionSummary
//                .builder()
//                .customerId(customerId)
//                .month(month)
//                .currentBalance(getCurrentBalance(transactions))
//                .classification(getClassification(monthlyTransactions))
//                .transactions(convertToTransactionDtos(monthlyTransactions))
//                .build();
//
//        return ResponseEntity
//                .ok()
//                .body(transactionSummary);
//    }
//
//    /**
//     * Converts a list of transactions to its Dto representation.
//     *
//     * @param transactions List of transactions to convert
//     * @return List of TransactionDtos
//     */
//    private List<TransactionDto> convertToTransactionDtos(List<Transaction> transactions) {
//        return transactions.stream()
//                .map(transaction -> convertToDto(transaction))
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Returns the total balance given the list of transactions passed as argument.
//     *
//     * @param transactions List of transactions
//     * @return Total balance
//     */
//    private double getCurrentBalance(List<Transaction> transactions) {
//        return BigDecimal.valueOf(transactions.stream().mapToDouble(t -> t.getAmount().doubleValue()).sum())
//                .setScale(2, RoundingMode.CEILING)
//                .doubleValue();
//    }
//
//    /**
//     * Returns the related monthly transactions from an input list of transactions.
//     *
//     * @param transactions List of transactions
//     * @param month        Month filter
//     * @return Monthly transactions
//     */
//    private List<Transaction> getMonthlyTransactions(List<Transaction> transactions, int month) {
//
//        LocalDateTime firstDayOfMonthDate = LocalDateTime.of(2016, month, 1, 0, 0, 0);
//        LocalDateTime lastDayOfMonthDate = firstDayOfMonthDate.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);
//
//        return transactions
//                .stream()
//                .filter(t -> t.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isAfter(firstDayOfMonthDate)
//                        && t.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isBefore(lastDayOfMonthDate))
//                .collect(Collectors.toList());
//
//    }

}
