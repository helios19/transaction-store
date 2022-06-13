package com.abn.amro.transaction.service;

import com.abn.amro.transaction.dto.TransactionSummary;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Service class implementing logic to build csv report from a list of transactions.
 *
 * @see CsvService
 */
@Service
@Slf4j
public class CsvServiceImpl implements CsvService {
    private static final String[] HEADERS = {"Client Information", "Production Information", "Date", "Total Transaction Amount"};
    private static final CSVFormat FORMAT = CSVFormat.DEFAULT.withHeader(HEADERS);

    /**
     * Loads data into csv
     *
     * @param transactionSummaries
     * @return
     */
    @Override
    public ByteArrayInputStream loadInputStream(final List<TransactionSummary> transactionSummaries) {
        return writeDataToCsv(transactionSummaries);
    }

    /**
     * Write transaction to csv.
     *
     * @param transactionSummaries
     * @return Byte input stream of the transaction summaries
     */
    private ByteArrayInputStream writeDataToCsv(final List<TransactionSummary> transactionSummaries) {
        log.info("Writing data to the csv printer");
        try (final ByteArrayOutputStream stream = new ByteArrayOutputStream();
             final CSVPrinter printer = new CSVPrinter(new PrintWriter(stream), FORMAT)) {
            transactionSummaries.stream().forEach(transactionSummary -> {
                Try.run(() -> printer.printRecord(
                                transactionSummary.getClientInformation(),
                                transactionSummary.getProductInformation(),
                                transactionSummary.getDate(),
                                transactionSummary.getTotalTransactionAmount()))
                        .getOrElseThrow(throwable -> new RuntimeException(throwable));
            });
            printer.flush();
            return new ByteArrayInputStream(stream.toByteArray());
        } catch (final IOException e) {
            throw new RuntimeException("Csv writing error: ", e);
        }
    }
}
