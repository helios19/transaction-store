package com.abn.amro.common;

import com.abn.amro.transaction.model.Transaction;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.abn.amro.common.utils.ClassUtils.HUNDRED;
import static com.abn.amro.common.utils.ClassUtils.TEN_MILLIONS;
import static com.abn.amro.common.utils.ClassUtils.toDate;

public class ClassTestUtils {

    public static final Transaction TRANSACTION_SAMPLE = Transaction
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
            .build();

    public static final Transaction TRANSACTION_SAMPLE_2 = Transaction
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
            .build();



}
