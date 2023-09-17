package com.wex.transaction.controller;

import com.wex.Application;
import com.wex.transaction.repository.TransactionRepository;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static com.wex.common.ClassTestUtils.TRANSACTION_SAMPLE;
import static com.wex.common.ClassTestUtils.TRANSACTION_SAMPLE_2;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {Application.class})
@ContextConfiguration(name = "contextWithFakeBean")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransactionControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TransactionRepository transactionRepository;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    public void setUp() throws Exception {
        transactionRepository.deleteAll();
        transactionRepository.saveAll(Arrays.asList(TRANSACTION_SAMPLE, TRANSACTION_SAMPLE_2));
    }

    @AfterEach
    public void tearDown() {
        transactionRepository.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    @Order(1)
    void shouldReturnConvertTransactionAmount() {
        // Given
        String url = "http://localhost:" + port + "/transactions/727/Australia";

        // When
        ResponseEntity<LinkedHashMap> response = testRestTemplate.getForEntity(url, LinkedHashMap.class);
        LinkedHashMap transactionDto = response.getBody();

        // Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(transactionDto.get("description"), "transaction 1");
        assertEquals(transactionDto.get("country"), "Australia");
        assertEquals(transactionDto.get("transactionDate"), "2010-09-10");
        assertEquals(transactionDto.get("amount"), "13967.50");
    }

    @Test
    @Order(2)
    void shouldReturnAllTransactions() throws JSONException {

        // Given
        String url = "http://localhost:" + port + "/transactions/";

        // When
        ResponseEntity<List> response = testRestTemplate.getForEntity(url, List.class);

        List<LinkedHashMap> transactionDtos = (List<LinkedHashMap>) response.getBody();

        // Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(transactionDtos.size(), 2);
        assertEquals(transactionDtos.get(0).get("description"), "transaction 1");
        assertEquals(transactionDtos.get(0).get("country"), "United States");
        assertEquals(transactionDtos.get(0).get("transactionDate"), "2010-09-10");
        assertEquals(transactionDtos.get(0).get("amount"), "9250.00");
        assertEquals(transactionDtos.get(1).get("description"), "transaction 2");
        assertEquals(transactionDtos.get(1).get("country"), "United States");
        assertEquals(transactionDtos.get(1).get("transactionDate"), "2010-09-11");
        assertEquals(transactionDtos.get(1).get("amount"), "9980.00");
    }

}