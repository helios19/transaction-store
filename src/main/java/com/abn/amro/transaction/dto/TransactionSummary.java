package com.abn.amro.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Transaction Summary DTO class gathering transaction details to be displayed in report.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionSummary {

    private String clientInformation;
    private String productInformation;
    private double totalTransactionAmount;
    private Date date;

}
