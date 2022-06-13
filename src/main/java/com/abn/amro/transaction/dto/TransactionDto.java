package com.abn.amro.transaction.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Transaction DTO class representing a transaction.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionDto {
    private Long id;
    private String recordCode;
    private String clientType;
    private String clientNum;
    private String acctNum;
    private String subAcctNum;
    private String oppositePartyCode;
    private String productGroupCode;
    private String exchangeCode;
    private String symbol;
    private String expirationDate;
    private String currencyCode;
    private String movementCode;
    private String buySellCode;
    private String qttyLongSign;
    private BigInteger qttyLong;
    private String qttyShortSign;
    private BigInteger qttyShort;
    private BigDecimal exchBrokerFeeDec;
    private String exchBrokerFeeDc;
    private String exchBrokerFeeCurCode;
    private BigDecimal clearingFeeDec;
    private String clearingFeeDc;
    private String clearingFeeCurCode;
    private BigDecimal commission;
    private String commissionDc;
    private String commissionCurCode;
    private Date transactionDate;
    private String futureReference;
    private String ticketNumber;
    private String externalNumber;
    private BigDecimal transactionPriceDec;
    private String traderInitials;
    private String oppositeTraderId;
    private String openCloseCode;
    private String filler;
}


