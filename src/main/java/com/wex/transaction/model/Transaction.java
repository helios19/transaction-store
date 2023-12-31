package com.wex.transaction.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wex.common.utils.ClassUtils;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Plain java class representing an transaction resource.
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

    @NotBlank(message = "Description is mandatory")
    private String description;

    private String country;

    @NotNull(message = "TransactionDate is mandatory")
    @JsonFormat(pattern = ClassUtils.DATE_FORMAT_PATTERN)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date transactionDate;

    @NotNull(message = "Amount is mandatory")
    private BigDecimal amount;
}
