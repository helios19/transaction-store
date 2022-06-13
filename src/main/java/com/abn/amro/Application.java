package com.abn.amro;

import com.abn.amro.transaction.model.Transaction;
import com.abn.amro.transaction.repository.TransactionRepository;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.Collectors;

import static com.abn.amro.common.utils.ClassUtils.HUNDRED;
import static com.abn.amro.common.utils.ClassUtils.TEN_MILLIONS;
import static com.abn.amro.common.utils.ClassUtils.toDate;

/**
 * Main Spring Boot Application class. Note that a {@link CommandLineRunner} is created
 * to initialize the Mongo database with a set of transactions.
 */
@EnableMongoRepositories(basePackages = "com.abn.amro.transaction.repository")
@SpringBootApplication
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static final void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Initializes the Mongodb database with a set of transactions from data.txt file.
     *
     * @param transactionRepository Transaction repository to save the transactions
     * @return CommandLineRunner
     */
    @Bean
    CommandLineRunner init(TransactionRepository transactionRepository) {

        LOG.info("start initializing mongodb...");

        try {
            return (evt) -> transactionRepository.saveOrUpdate(

                    new BufferedReader(
                            new InputStreamReader(ResourceUtils.getURL("classpath:Input.txt").openStream()))
                            .lines()
                            .filter(s -> !Strings.isNullOrEmpty(s) && s.length() >= 303)
                            .map(s -> Transaction
                                    .builder()
                                    .recordCode(s.substring(0, 3))
                                    .clientType(s.substring(3, 7))
                                    .clientNum(s.substring(7, 11))
                                    .acctNum(s.substring(11, 15))
                                    .subAcctNum(s.substring(15, 19))
                                    .oppositePartyCode(s.substring(19, 25))
                                    .productGroupCode(s.substring(25, 27))
                                    .exchangeCode(s.substring(27, 31))
                                    .symbol(s.substring(31, 37))
                                    .expirationDate(s.substring(37, 45))
                                    .currencyCode(s.substring(45, 48))
                                    .movementCode(s.substring(48, 50))
                                    .buySellCode(s.substring(50, 51))
                                    .qttyLongSign(s.substring(51, 52))
                                    .qttyLong(new BigInteger(s.substring(52, 62)))
                                    .qttyShortSign(s.substring(62, 63))
                                    .qttyShort(new BigInteger(s.substring(63, 73)))
                                    .exchBrokerFeeDec(new BigDecimal(s.substring(73, 85)).divide(HUNDRED))
                                    .exchBrokerFeeDc(s.substring(85, 86))
                                    .exchBrokerFeeCurCode(s.substring(86, 89))
                                    .clearingFeeDec(new BigDecimal(s.substring(89, 101)).divide(HUNDRED))
                                    .clearingFeeDc(s.substring(101, 102))
                                    .clearingFeeCurCode(s.substring(102, 105))
                                    .commission(new BigDecimal(s.substring(105, 117)).divide(HUNDRED))
                                    .commissionDc(s.substring(117, 118))
                                    .commissionCurCode(s.substring(118, 121))
                                    .transactionDate(toDate(s.substring(121, 129)))
                                    .futureReference(s.substring(129, 135))
                                    .ticketNumber(s.substring(135, 141))
                                    .externalNumber(s.substring(141, 147))
                                    .transactionPriceDec(new BigDecimal(s.substring(147, 162)).divide(TEN_MILLIONS))
                                    .traderInitials(s.substring(162, 168))
                                    .oppositeTraderId(s.substring(168, 175))
                                    .openCloseCode(s.substring(175, 176))
                                    .filler(s.substring(176, 303))
                                    .build()
//                                    .customer(s[0])
//                                    .date(toDate(s[1]))
//                                    .amount(new BigDecimal(s[2]))
//                                    .description(s[3])
//                                    .build()
                            )
                            .collect(Collectors.toList()).toArray(new Transaction[]{})
            );
        } finally {
            LOG.info("end of mongodb initialization...");
        }
    }

}
