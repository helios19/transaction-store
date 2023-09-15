package com.wex;

import com.google.common.base.Strings;
import com.wex.transaction.model.RateExchange;
import com.wex.transaction.repository.RateExchangeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import static com.wex.common.utils.ClassUtils.toDate;

/**
 * Main Spring Boot Application class. Note that a {@link CommandLineRunner} is created
 * to initialize the H2 database with a set of transactions.
 */
@EntityScan("com.wex.transaction.model")
@EnableJpaRepositories("com.wex.transaction.repository")
@SpringBootApplication
@Slf4j
public class Application {
    public static final void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Initializes the database with a set of rateExchanges from local static file.
     *
     * @param rateExchangeRepository Transaction repository to save the rateExchanges
     * @return CommandLineRunner
     */
    @Bean
    CommandLineRunner init(RateExchangeRepository rateExchangeRepository) {

        log.info("start initializing database...");

        try {
            return (evt) -> rateExchangeRepository.saveAll(

                    new BufferedReader(
                            new InputStreamReader(ResourceUtils.getURL("classpath:RprtRateXchg_20220701_20230630.csv").openStream()))
                            .lines()
                            .skip(1)
                            .filter(s -> !Strings.isNullOrEmpty(s))
                            .map(s -> {

                                String[] cols = s.split(",");

                                return RateExchange
                                                .builder()
                                                .recordDate(toDate(cols[0]))
                                                .country(cols[1])
                                                .currency(cols[2])
                                                .description(cols[3])
                                                .rate(new BigDecimal(cols[4]))
                                                .effectiveDate(toDate(cols[5]))
                                                .sourceLineNum(Integer.valueOf(cols[6]))
                                                .fiscalYear(Integer.valueOf(cols[7]))
                                                .fiscalQuarterNum(Integer.valueOf(cols[8]))
                                                .calendarYear(Integer.valueOf(cols[9]))
                                                .calendarQuarterNum(Integer.valueOf(cols[10]))
                                                .calendarMonthNum(Integer.valueOf(cols[11]))
                                                .calendarDayNum(Integer.valueOf(cols[12]))
                                                .build();
                                    })
                            .collect(Collectors.toList())
            );
        } finally {
            log.info("end of exchange rate loading...");
        }
    }

}
