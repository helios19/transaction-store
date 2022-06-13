package com.abn.amro.common.utils;

import com.abn.amro.transaction.dto.TransactionDto;
import com.abn.amro.transaction.exception.InvalidParameterException;
import com.abn.amro.transaction.exception.InvalidTransactionException;
import com.abn.amro.transaction.model.Transaction;
import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.tuple.Pair;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Utils class providing convenient factory and helper methods for {@link Transaction} resources.
 */
public class ClassUtils {
    public static final String COUNTERS_COLLECTION_NAME = "counters";
    public static final String TRANSACTIONS_COLLECTION_NAME = "transactions";
    public static final String DATE_FORMAT_PATTERN = "yyyyMMdd";
    public static final DateTimeFormatter FORMATTER = ofPattern("d/MM/yyyy h:mm:ss a");
    public static final DateTimeFormatter DATE_FORMATTER = ofPattern(DATE_FORMAT_PATTERN);

    public static final int DEFAULT_PAGE_SIZE = 50;
    public static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public static BigDecimal HUNDRED = new BigDecimal("100");

    public static BigDecimal TEN_MILLIONS = new BigDecimal("10000000");

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

        if (transaction == null || transaction.getCustomer() == null) {
            throw new InvalidTransactionException(transaction);
        }

    }

    /**
     * Validates transaction identifier field.
     *
     * @param customerId Transaction identifier
     * @return Transaction identifier if valid
     * @throws InvalidParameterException in case transaction identifier is invalid
     */
    public static String validateCustomerId(String customerId) {
        if (Ints.tryParse(customerId) == null) {
            throw new InvalidParameterException("customerId", customerId);
        }

        return customerId;
    }

    /**
     * Validates transaction identifier field.
     *
     * @param month Month
     * @return Month if valid
     * @throws InvalidParameterException in case month is invalid
     */
    public static int validateMonth(String month) {

        Integer iMonth = Ints.tryParse(month);

        if (iMonth == null || iMonth <= 0) {
            throw new InvalidParameterException("month", month);
        }

        return iMonth;
    }

    /**
     * Returns start and end date given month number.
     *
     * @param month Month
     * @return Pair of start and end date
     */
    public static Pair<Date, Date> toStartEndDate(int month) {
        LocalDateTime firstDayOfMonthDate = LocalDateTime.of(2016, month, 1, 0, 0, 0);
        LocalDateTime lastDayOfMonthDate = firstDayOfMonthDate.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);

        return Pair.of(
                Date.from(firstDayOfMonthDate.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(lastDayOfMonthDate.atZone(ZoneId.systemDefault()).toInstant()));
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
        transactionDto.setDate(fromDate(transaction.getDate()));
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
                .add(Strings.nullToEmpty(transaction.getClientType()).trim())
                .add(Strings.nullToEmpty(transaction.getClientNum()).trim())
                .add(Strings.nullToEmpty(transaction.getAcctNum()).trim())
                .add(Strings.nullToEmpty(transaction.getSubAcctNum()).trim())
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
                .add(Strings.nullToEmpty(transaction.getExchangeCode()).trim())
                .add(Strings.nullToEmpty(transaction.getProductGroupCode()).trim())
                .add(Strings.nullToEmpty(transaction.getSymbol()).trim())
                .add(Strings.nullToEmpty(transaction.getExpirationDate()).trim())
                .toString();
    }

    /**
     * Returns the total balance given the list of transactions passed as argument.
     *
     * @param transactions List of transactions
     * @return Total transaction amount
     */
    public static double getTotalTransactionAmount(List<Transaction> transactions) {
        return BigDecimal.valueOf(transactions.stream().mapToDouble(t -> (t.getQttyLong().subtract(t.getQttyShort()).doubleValue())).sum())
                .setScale(2, RoundingMode.CEILING)
                .doubleValue();
    }


//    public static final void main(String... args) {
//
//        System.out.println("toDate : " + toDate("31/05/2016 4:20:23 PM"));
//        System.out.println("toDate : " + toDate("31/5/2016 9:15:24 AM"));
//        System.out.println("toDate : " + toDate("31/5/2016 9:15:24 PM"));
//    }
}
