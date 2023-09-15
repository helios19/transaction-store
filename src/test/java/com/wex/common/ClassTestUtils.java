package com.wex.common;

import com.wex.transaction.dto.TransactionSummary;
import com.wex.transaction.model.Transaction;
import com.wex.common.utils.ClassUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ClassTestUtils {

    public static final TransactionSummary TRANSACTION_SUMMARY = TransactionSummary
            .builder()
            .clientInformation("CL  432100020001")
            .productInformation("SGX FUNK    20100910")
            .date(ClassUtils.toDate("20100910"))
            .totalTransactionAmount(0)
            .build();

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
            .exchBrokerFeeDec(new BigDecimal("000000000060").divide(ClassUtils.HUNDRED))
            .exchBrokerFeeDc("D")
            .exchBrokerFeeCurCode("USD")
            .clearingFeeDec(new BigDecimal("000000000030").divide(ClassUtils.HUNDRED))
            .clearingFeeDc("D")
            .clearingFeeCurCode("USD")
            .commission(new BigDecimal("000000000000").divide(ClassUtils.HUNDRED))
            .commissionDc("D")
            .commissionCurCode("JPY")
            .transactionDate(ClassUtils.toDate("20100820"))
            .futureReference("001238")
            .ticketNumber("0     ")
            .externalNumber("688032")
            .transactionPriceDec(new BigDecimal("000092500000000").divide(ClassUtils.TEN_MILLIONS))
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
            .exchBrokerFeeDec(new BigDecimal("000000000060").divide(ClassUtils.HUNDRED))
            .exchBrokerFeeDc("D")
            .exchBrokerFeeCurCode("USD")
            .clearingFeeDec(new BigDecimal("000000000030").divide(ClassUtils.HUNDRED))
            .clearingFeeDc("D")
            .clearingFeeCurCode("USD")
            .commission(new BigDecimal("000000000000").divide(ClassUtils.HUNDRED))
            .commissionDc("D")
            .commissionCurCode("JPY")
            .transactionDate(ClassUtils.toDate("20100820"))
            .futureReference("001238")
            .ticketNumber("0     ")
            .externalNumber("688032")
            .transactionPriceDec(new BigDecimal("000092500000000").divide(ClassUtils.TEN_MILLIONS))
            .traderInitials("      ")
            .oppositeTraderId("       ")
            .openCloseCode(" ")
            .filler("O                                                                                                                              ")
            .build();



}
