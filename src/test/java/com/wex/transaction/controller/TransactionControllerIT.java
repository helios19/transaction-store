package com.wex.transaction.controller;

import com.wex.Application;
import com.wex.transaction.repository.TransactionRepository;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;

import static com.wex.common.ClassTestUtils.TRANSACTION_SAMPLE;
import static com.wex.common.ClassTestUtils.TRANSACTION_SAMPLE_2;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {Application.class})
@ContextConfiguration(name = "contextWithFakeBean")
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
    void shouldReturnAllTransactions() throws JSONException {

        // Given
        String url = "http://localhost:" + port + "/transactions/";
        String expected = "[{\"id\":727,\"description\":\"transaction 1\",\"country\":\"United States\",\"transactionDate\":\"2010-09-10\",\"amount\":\"9250.00\"},{\"id\":728,\"description\":\"transaction 2\",\"country\":\"United State\",\"transactionDate\":\"2010-09-11\",\"amount\":\"9980.00\"}]";

        // When
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        // Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void shouldReturnConvertTransactionAmount() {
        // Given
        String url = "http://localhost:" + port + "/transactions/727/Australia";
        String expected = "{\"id\":727,\"description\":\"transaction 1\",\"country\":\"Australia\",\"transactionDate\":\"2010-09-10\",\"amount\":\"13967.50\"}";

        // When
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        System.out.println("response.getBody() : " + response.getBody());

        // Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(expected.replaceAll("\\n|\\r\\n", System.getProperty("line.separator")).trim(),
                response.getBody().replaceAll("\\n|\\r\\n", System.getProperty("line.separator")).trim());
    }

}