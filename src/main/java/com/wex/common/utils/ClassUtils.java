package com.wex.common.utils;

import com.wex.transaction.dto.TransactionDto;
import com.wex.transaction.exception.InvalidTransactionException;
import com.wex.transaction.model.Transaction;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.StringJoiner;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Utils class providing convenient factory and helper methods for {@link Transaction} resources.
 */
public class ClassUtils {
    public static final String TRANSACTIONS_COLLECTION_NAME = "transactions";
    public static final String RATE_EXCHANGE_COLLECTION_NAME = "rateExchanges";
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter FORMATTER = ofPattern("d/MM/yyyy h:mm:ss a");
    public static final DateTimeFormatter DATE_FORMATTER = ofPattern(DATE_FORMAT_PATTERN);
    public static final int DEFAULT_PAGE_SIZE = 50;
    public static final ModelMapper MODEL_MAPPER = new ModelMapper();
    public static BigDecimal HUNDRED = new BigDecimal("100");
    public static BigDecimal TEN_MILLIONS = new BigDecimal("10000000");
    public static String CSV_FILENAME = "transaction-summary-report.csv";
    public static final String MEDIATYPE_TEXT_HTML = "text/csv";

    private ClassUtils() {
    }

    /**
     * Validates transaction fields.
     *
     * @param transaction Transaction instance to validate
     * @throws InvalidTransactionException in case one of the transaction fields is invalid
     */
    public static void validateTransaction(Transaction transaction) {

        // validate transaction

//        if (transaction == null || transaction.getClientNum() == null) {
//            throw new InvalidTransactionException(transaction);
//        }

    }

    /**
     * Converts {@code isoDate} argument to {@link Date}.
     *
     * @param isoDate character sequence to convert
     * @return Date instance
     */
    public static Date toDate(String isoDate) {
        return Date.from(
                LocalDate.parse(isoDate, DATE_FORMATTER)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant());
    }

    /**
     * Converts {@link Date} argument to {@code isoDate}.
     *
     * @param date Date to format
     * @return Date instance
     */
    public static String fromDate(Date date) {
        return DATE_FORMATTER.format(LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }

    /**
     * Converts a {@link Transaction} instance into a {@link TransactionDto} object.
     *
     * @param transaction Transaction to convert
     * @return TransactionDto
     */
    public static TransactionDto convertToDto(Transaction transaction) {
        TransactionDto transactionDto = MODEL_MAPPER.map(transaction, TransactionDto.class);
//        transactionDto.setDate(fromDate(transaction.getDate()));
        return transactionDto;
    }

    /**
     * Returns the client information from a given transaction. It is a combination
     * of the ClientType, ClientNum, AcctNum and the SubAcctNum fields.
     *
     * @param transaction Transaction to extract the client information from
     * @return Client Information
     */
    public static String getClientInformation(Transaction transaction) {
        return new StringJoiner("")
//                .add(Strings.nullToEmpty(transaction.getClientType()).trim())
//                .add(Strings.nullToEmpty(transaction.getClientNum()).trim())
//                .add(Strings.nullToEmpty(transaction.getAcctNum()).trim())
//                .add(Strings.nullToEmpty(transaction.getSubAcctNum()).trim())
                .toString();
    }

    /**
     * Returns the product information from a given transaction. It is a combination
     * of the ExchangeCode, ProductGroupCode, Symbol and the ExpirationDate fields.
     *
     * @param transaction Transaction to extract the product information from
     * @return Product Information
     */
    public static String getProductInformation(Transaction transaction) {
        return new StringJoiner("")
//                .add(Strings.nullToEmpty(transaction.getExchangeCode()).trim())
//                .add(Strings.nullToEmpty(transaction.getProductGroupCode()).trim())
//                .add(Strings.nullToEmpty(transaction.getSymbol()).trim())
//                .add(Strings.nullToEmpty(transaction.getExpirationDate()).trim())
                .toString();
    }

    /**
     * Returns the total balance given the list of transactions passed as argument.
     *
     * @param transactions List of transactions
     * @return Total transaction amount
     */
//    public static double getTotalTransactionAmount(List<Transaction> transactions) {
//        return BigDecimal.valueOf(transactions.stream().mapToDouble(t -> (t.getQttyLong().subtract(t.getQttyShort()).doubleValue())).sum())
//                .setScale(2, RoundingMode.CEILING)
//                .doubleValue();
//    }
}
