package com.wex.transaction.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * RateExchange DTO class representing a rate exchange.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RateExchangeDto {
    private Long id;
    private String country;
    private String currency;
    private String rate;
}


