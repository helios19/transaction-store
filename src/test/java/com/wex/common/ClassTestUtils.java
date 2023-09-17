package com.wex.common;

import com.wex.common.utils.ClassUtils;
import com.wex.ratexchg.model.RateExchange;
import com.wex.transaction.model.Transaction;

import java.math.BigDecimal;

public class ClassTestUtils {

    public static final Transaction TRANSACTION_SAMPLE = Transaction
            .builder()
            .id(1L)
            .description("transaction 1")
            .country("United States")
            .transactionDate(ClassUtils.toDate("2023-09-10"))
            .amount(new BigDecimal("000092500000000").divide(ClassUtils.TEN_MILLIONS))
            .build();

    public static final Transaction TRANSACTION_SAMPLE_2 = Transaction
            .builder()
            .id(2L)
            .description("transaction 2")
            .country("United States")
            .transactionDate(ClassUtils.toDate("2023-09-11"))
            .amount(new BigDecimal("000099800000000").divide(ClassUtils.TEN_MILLIONS))
            .build();

    public static final Transaction TRANSACTION_SAMPLE_3 = Transaction
            .builder()
            .id(2L)
            .description("transaction 3")
            .country("United States")
            .transactionDate(ClassUtils.toDate("2010-09-11"))
            .amount(new BigDecimal("000099800000000").divide(ClassUtils.TEN_MILLIONS))
            .build();

    public static final RateExchange RATEEXCHANGE_SAMPLE = RateExchange
            .builder()
            .id(1L)
            .recordDate(ClassUtils.toDate("2023-03-31"))
            .country("Australia")
            .currency("Dollar")
            .description("Australia-Dollar")
            .rate(new BigDecimal(1.494))
            .effectiveDate(ClassUtils.toDate("2023-03-31"))
            .sourceLineNum(10)
            .fiscalYear(2023)
            .fiscalQuarterNum(2)
            .calendarYear(2023)
            .calendarQuarterNum(1)
            .calendarMonthNum(3)
            .calendarDayNum(31)
            .build();

    public static final RateExchange RATEEXCHANGE_SAMPLE_2 = RateExchange
            .builder()
            .id(2L)
            .recordDate(ClassUtils.toDate("2023-06-30"))
            .country("Argentina")
            .currency("Peso")
            .description("Argentina-Peso")
            .rate(new BigDecimal(365.5))
            .effectiveDate(ClassUtils.toDate("2023-08-15"))
            .sourceLineNum(7)
            .fiscalYear(2023)
            .fiscalQuarterNum(3)
            .calendarYear(2023)
            .calendarQuarterNum(2)
            .calendarMonthNum(6)
            .calendarDayNum(30)
            .build();
}
