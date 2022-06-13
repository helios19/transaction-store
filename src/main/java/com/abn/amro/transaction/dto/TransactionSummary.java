package com.abn.amro.transaction.dto;

import com.abn.amro.transaction.service.ClassificationEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Transaction Summary DTO class gathering customer transaction details including {@link #customerId}, {@link #month}
 * {@link #classification} and {@link #transactions}.
 *
 * @see TransactionDto
 * @see ClassificationEnum
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

//
//    private String customerId;
//    private String month;
//    private double currentBalance;
//    private List<ClassificationEnum> classification;
//    private List<TransactionDto> transactions;
}
