package com.abn.amro.transaction.service

import com.abn.amro.transaction.dto.TransactionSummary
import com.abn.amro.transaction.model.Transaction
import spock.lang.Specification
import spock.lang.Unroll

import static com.abn.amro.common.utils.ClassUtils.*

class TransactionSummaryServiceImplTest  extends Specification {
    def transactionSummaryService = new TransactionSummaryServiceImpl()

    def static transactionSummary = TransactionSummary
            .builder()
            .clientInformation("CL  432100020001")
            .productInformation("SGX FUNK    20100910")
            .date(toDate("20100910"))
            .totalTransactionAmount(0)
            .build()

    def static transaction1 = Transaction
            .builder()
            .recordCode("315")
            .clientType("CL  ")
            .clientNum("4321")
            .acctNum("0002")
            .subAcctNum("0001")
            .oppositePartyCode("SGXDC ")
            .productGroupCode("FU")
            .exchangeCode("SGX ")
            .symbol("NK    ")
            .expirationDate("20100910")
            .currencyCode("JPY")
            .movementCode("01")
            .buySellCode("B")
            .qttyLongSign(" ")
            .qttyLong(new BigInteger("0000000001"))
            .qttyShortSign(" ")
            .qttyShort(new BigInteger("0000000000"))
            .exchBrokerFeeDec(new BigDecimal("000000000060").divide(HUNDRED))
            .exchBrokerFeeDc("D")
            .exchBrokerFeeCurCode("USD")
            .clearingFeeDec(new BigDecimal("000000000030").divide(HUNDRED))
            .clearingFeeDc("D")
            .clearingFeeCurCode("USD")
            .commission(new BigDecimal("000000000000").divide(HUNDRED))
            .commissionDc("D")
            .commissionCurCode("JPY")
            .transactionDate(toDate("20100820"))
            .futureReference("001238")
            .ticketNumber("0     ")
            .externalNumber("688032")
            .transactionPriceDec(new BigDecimal("000092500000000").divide(TEN_MILLIONS))
            .traderInitials("      ")
            .oppositeTraderId("       ")
            .openCloseCode(" ")
            .filler("O                                                                                                                              ")
            .build()

    def static transaction2 = Transaction
            .builder()
            .recordCode("315")
            .clientType("CL  ")
            .clientNum("4321")
            .acctNum("0002")
            .subAcctNum("0001")
            .oppositePartyCode("SGXDC ")
            .productGroupCode("FU")
            .exchangeCode("SGX ")
            .symbol("NK    ")
            .expirationDate("20100910")
            .currencyCode("JPY")
            .movementCode("01")
            .buySellCode("B")
            .qttyLongSign(" ")
            .qttyLong(new BigInteger("0000000003"))
            .qttyShortSign(" ")
            .qttyShort(new BigInteger("0000000000"))
            .exchBrokerFeeDec(new BigDecimal("000000000060").divide(HUNDRED))
            .exchBrokerFeeDc("D")
            .exchBrokerFeeCurCode("USD")
            .clearingFeeDec(new BigDecimal("000000000030").divide(HUNDRED))
            .clearingFeeDc("D")
            .clearingFeeCurCode("USD")
            .commission(new BigDecimal("000000000000").divide(HUNDRED))
            .commissionDc("D")
            .commissionCurCode("JPY")
            .transactionDate(toDate("20100820"))
            .futureReference("001238")
            .ticketNumber("0     ")
            .externalNumber("688032")
            .transactionPriceDec(new BigDecimal("000092500000000").divide(TEN_MILLIONS))
            .traderInitials("      ")
            .oppositeTraderId("       ")
            .openCloseCode(" ")
            .filler("O                                                                                                                              ")
            .build()

    def static transaction_single1 = [
            transaction1
    ]

    def static transaction_single2 = [
            transaction2
    ]

    def static transaction_multi = [
            transaction1,
            transaction2
    ]


    @Unroll
    def "should build transaction summaries"() {

        given:

        when: "invoking classification service"
        TransactionSummary[] transactionSummaries = transactionSummaryService.buildTransactionSummaryReport(transactions)

        then:
        transactionSummaries.sort() == expectTransactionSummaries.sort()

        where:
        transactions                        | expectTransactionSummaries
        transaction_single1                 | [transactionSummary]
        transaction_single2                 | [transactionSummary]
        transaction_multi                   | [transactionSummary]

    }
}
