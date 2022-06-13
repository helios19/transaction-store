package com.abn.amro.transaction.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

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
@Document
@CompoundIndexes({
        @CompoundIndex(name = "transaction_customer_date_idx", def = "{'customer': 1, 'date': 1}")
})
@Data
@Builder
public class Transaction {
    @Id
    private String id;



    @Field
    @Indexed
    @NotNull
    private String recordCode;

    @Field
    @Indexed
    @NotNull
    private String clientType;

    @Field
    @Indexed
    @NotNull
    private String clientNum;

    @Field
    @Indexed
    @NotNull
    private String acctNum;

    @Field
    @Indexed
    @NotNull
    private String subAcctNum;

    @Field
    @Indexed
    @NotNull
    private String oppositePartyCode;

    @Field
    @Indexed
    @NotNull
    private String productGroupCode;

    @Field
    @Indexed
    @NotNull
    private String exchangeCode;

    @Field
    @Indexed
    @NotNull
    private String symbol;

    @Field
    @Indexed
    @NotNull
//    @JsonFormat(pattern = DATE_FORMAT_PATTERN)
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String expirationDate;

    @Field
    @Indexed
    @NotNull
    private String currencyCode;

    @Field
    @Indexed
    @NotNull
    private String movementCode;

    @Field
    @Indexed
    @NotNull
    private String buySellCode;

    @Field
    @Indexed
    @NotNull
    private String qttyLongSign;

    @Field
    @Indexed
    @NotNull
    private BigInteger qttyLong;

    @Field
    @Indexed
    @NotNull
    private String qttyShortSign;

    @Field
    @Indexed
    @NotNull
    private BigInteger qttyShort;

    @Field
    @Indexed
    @NotNull
    private BigDecimal exchBrokerFeeDec;

    @Field
    @Indexed
    @NotNull
    private String exchBrokerFeeDc;

    @Field
    @Indexed
    @NotNull
    private String exchBrokerFeeCurCode;

    @Field
    @Indexed
    @NotNull
    private BigDecimal clearingFeeDec;

    @Field
    @Indexed
    @NotNull
    private String clearingFeeDc;

    @Field
    @Indexed
    @NotNull
    private String clearingFeeCurCode;

    @Field
    @Indexed
    @NotNull
    private BigDecimal commission;

    @Field
    @Indexed
    @NotNull
    private String commissionDc;

    @Field
    @Indexed
    @NotNull
    private String commissionCurCode;

    @Field
    @Indexed
    @NotNull
    @JsonFormat(pattern = DATE_FORMAT_PATTERN)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date transactionDate;

    @Field
    @Indexed
    @NotNull
    private String futureReference;

    @Field
    @Indexed
    @NotNull
    private String ticketNumber;

    @Field
    @Indexed
    @NotNull
    private String externalNumber;

    @Field
    @Indexed
    @NotNull
    private BigDecimal transactionPriceDec;

    @Field
    @Indexed
    @NotNull
    private String traderInitials;

    @Field
    @Indexed
    @NotNull
    private String oppositeTraderId;

    @Field
    @Indexed
    @NotNull
    private String openCloseCode;

    @Field
    @Indexed
    @NotNull
    private String filler;




    @Field
    @Indexed
    @NotNull
    private String customer;

    @Field
    @Indexed
    @NotNull
    @JsonFormat(pattern = DATE_FORMAT_PATTERN)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;

    @Field
    @NotNull
    private BigDecimal amount;

    @Field
    @NotNull
    private String description;
}
