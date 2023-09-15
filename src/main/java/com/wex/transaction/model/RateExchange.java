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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Plain java class representing an rate exchange resource.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RateExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @JsonFormat(pattern = ClassUtils.DATE_FORMAT_PATTERN)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date recordDate;

    @NotNull
    private String country;

    @NotNull
    private String currency;

    @NotNull
    private String description;

    @NotNull
    private BigDecimal rate;

    @NotNull
    @JsonFormat(pattern = ClassUtils.DATE_FORMAT_PATTERN)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date effectiveDate;

    @NotNull
    private Integer sourceLineNum;

    @NotNull
    private Integer fiscalYear;

    @NotNull
    private Integer fiscalQuarterNum;

    @NotNull
    private Integer calendarYear;

    @NotNull
    private Integer calendarQuarterNum;

    @NotNull
    private Integer calendarMonthNum;

    @NotNull
    private Integer calendarDayNum;


}
