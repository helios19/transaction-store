package com.wex.transaction.controller;

import com.wex.Application;
import com.wex.transaction.repository.TransactionRepository;
import com.wex.common.utils.ClassUtils;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
    void shouldReturnAllTransactionSummary() throws JSONException {

        // Given
        String url = "http://localhost:" + port + "/transactions/all";
        String expected = "[{\"clientInformation\":\"CL432100020001\",\"productInformation\":\"SGXFUNK20100910\",\"totalTransactionAmount\":4.0,\"date\":\"2010-08-19T14:00:00.000+00:00\"}]";

        // When
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        // Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void shouldReturnCsvTransactionSummary() {
        // Given
        String url = "http://localhost:" + port + "/transactions/export-csv";
        String expected = "Client Information,Production Information,Date,Total Transaction Amount\n" +
                "CL432100020001,SGXFUNK20100910,2010-08-20 00:00:00.0,4.0";

        // When
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        // Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(ClassUtils.MEDIATYPE_TEXT_HTML, response.getHeaders().getContentType().toString());
        assertEquals(expected.replaceAll("\\n|\\r\\n", System.getProperty("line.separator")).trim(),
                response.getBody().replaceAll("\\n|\\r\\n", System.getProperty("line.separator")).trim());
    }

}