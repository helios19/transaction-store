package com.wex.common.utils;

import com.wex.ratexchg.dto.RateExchangeDto;
import com.wex.ratexchg.model.RateExchange;
import com.wex.transaction.dto.TransactionDto;
import com.wex.transaction.exception.InvalidTransactionException;
import com.wex.transaction.model.Transaction;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

        MODEL_MAPPER.addConverter(new AbstractConverter<Date, String>() {
                                      @Override
                                      protected String convert(Date source) {
                                         return ClassUtils.fromDate(source);
                                      }});
        MODEL_MAPPER.addConverter(new AbstractConverter<BigDecimal, String>() {
            @Override
            protected String convert(BigDecimal source) {
                return source .setScale(2, RoundingMode.CEILING).toString();
            }});

        return MODEL_MAPPER.map(transaction, TransactionDto.class);
    }

    /**
     * Converts a {@link RateExchange} instance into a {@link RateExchangeDto} object.
     *
     * @param rateExchange RateExchange to convert
     * @return RateExchangeDto
     */
    public static RateExchangeDto convertToDto(RateExchange rateExchange) {
        return MODEL_MAPPER.map(rateExchange, RateExchangeDto.class);
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
