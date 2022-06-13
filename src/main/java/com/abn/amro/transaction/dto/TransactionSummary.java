package com.abn.amro.transaction.dto;

import com.abn.amro.transaction.service.ClassificationEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Transaction Summary DTO class gathering customer transaction details including {@link #customerId}, {@link #month}
 * {@link #classification} and {@link #transactions}.
 *
 * @see TransactionDto
 * @see ClassificationEnum
 */
@Data
@Builder
public class TransactionSummary {

    private String clientInformation;
    private String productInformation;
    private double totalTransactionAmount;
    private Date date;


    private String customerId;
    private String month;
    private double currentBalance;
    private List<ClassificationEnum> classification;
    private List<TransactionDto> transactions;
}
