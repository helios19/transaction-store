package com.abn.amro.transaction.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import static com.abn.amro.common.utils.ClassUtils.DATE_FORMAT_PATTERN;

/**
 * Plain java class representing an transaction resource.
 * <p>
 * <p>This class also declares a compound-index based on {@code customer} and {@code date} fields.</p>
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String recordCode;

    @NotNull
    private String clientType;

    @NotNull
    private String clientNum;

    @NotNull
    private String acctNum;

    @NotNull
    private String subAcctNum;

    @NotNull
    private String oppositePartyCode;

    @NotNull
    private String productGroupCode;

    @NotNull
    private String exchangeCode;

    @NotNull
    private String symbol;

    @NotNull
//    @JsonFormat(pattern = DATE_FORMAT_PATTERN)
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String expirationDate;

    @NotNull
    private String currencyCode;

    @NotNull
    private String movementCode;

    @NotNull
    private String buySellCode;

    @NotNull
    private String qttyLongSign;

    @NotNull
    private BigInteger qttyLong;

    @NotNull
    private String qttyShortSign;

    @NotNull
    private BigInteger qttyShort;

    @NotNull
    private BigDecimal exchBrokerFeeDec;

    @NotNull
    private String exchBrokerFeeDc;

    @NotNull
    private String exchBrokerFeeCurCode;

    @NotNull
    private BigDecimal clearingFeeDec;

    @NotNull
    private String clearingFeeDc;

    @NotNull
    private String clearingFeeCurCode;

    @NotNull
    private BigDecimal commission;

    @NotNull
    private String commissionDc;

    @NotNull
    private String commissionCurCode;

    @NotNull
    @JsonFormat(pattern = DATE_FORMAT_PATTERN)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date transactionDate;

    @NotNull
    private String futureReference;

    @NotNull
    private String ticketNumber;

    @NotNull
    private String externalNumber;

    @NotNull
    private BigDecimal transactionPriceDec;

    @NotNull
    private String traderInitials;

    @NotNull
    private String oppositeTraderId;

    @NotNull
    private String openCloseCode;

    @NotNull
    private String filler;

}
