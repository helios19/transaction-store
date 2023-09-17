package com.wex.ratexchg.service

import com.wex.common.utils.ClassUtils
import com.wex.ratexchg.model.RateExchange
import spock.lang.Specification
import spock.lang.Unroll

class RateExchangeServiceImplTest extends Specification {
    def rateExchangesService = new RateExchangeServiceImpl()

    def static rateExchange1 = RateExchange
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
            .build()

    def static rateExchange2 = RateExchange
            .builder()
            .id(1L)
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
            .build()

    def static rateExchange3 = RateExchange
            .builder()
            .id(1L)
            .recordDate(ClassUtils.toDate("2023-06-30"))
            .country("Belarus")
            .currency("New Ruble")
            .description("Belarus-New Ruble")
            .rate(new BigDecimal(3.265))
            .effectiveDate(ClassUtils.toDate("2023-08-15"))
            .sourceLineNum(16)
            .fiscalYear(2023)
            .fiscalQuarterNum(3)
            .calendarYear(2023)
            .calendarQuarterNum(2)
            .calendarMonthNum(6)
            .calendarDayNum(30)
            .build()

    @Unroll
    def "should convert transaction amount with a given rate exchange"() {

        given:

        when: "invoking rate exchange service converter"
        BigDecimal convertedAmount = rateExchangesService.convertAmount(amountToConvert, exchangeRate)

        then:
        convertedAmount.doubleValue() == expectedAmount

        where:
        amountToConvert         | exchangeRate              | expectedAmount
        1                       | rateExchange1             | 0
        1                       | rateExchange2             | 0
        1                       | rateExchange3             | 0

    }
}
