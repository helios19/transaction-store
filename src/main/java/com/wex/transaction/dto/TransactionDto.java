package com.wex.transaction.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String description;
    private String country;
    private String transactionDate;
    private String amount;
}


