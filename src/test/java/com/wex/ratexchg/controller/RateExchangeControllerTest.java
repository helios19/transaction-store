package com.wex.ratexchg.controller;

import com.google.common.collect.Lists;
import com.wex.ratexchg.dto.RateExchangeDto;
import com.wex.ratexchg.exception.RateExchangeNotFoundException;
import com.wex.ratexchg.service.RateExchangeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.wex.common.ClassTestUtils.RATEEXCHANGE_SAMPLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class RateExchangeControllerTest {
    @Mock
    private RateExchangeService rateExchangeService;

    @InjectMocks
    RateExchangeController controller;

    @Test
    public void shouldReturnAllRateExchanges() {

        // Given
        when(rateExchangeService.findAll())
                .thenReturn(Lists.newArrayList(RATEEXCHANGE_SAMPLE));

        // When
        ResponseEntity<List<RateExchangeDto>> response = controller.getRateExchanges();

        // Then
        assertThat(response).isNotNull();
        verify(rateExchangeService, times(1)).findAll();
        verifyNoMoreInteractions(rateExchangeService);
    }

    @Test
    public void shouldThrowRateExchangeNotFoundExceptionWhenNoRateExists() {
        // Given
        when(rateExchangeService.findAll())
                .thenReturn(Lists.newArrayList());

        // When
        RateExchangeNotFoundException thrown = assertThrows(
                RateExchangeNotFoundException.class,
                () -> controller.getRateExchanges(),
                "Expected getRateExchanges() to throw, but it didn't"
        );

        // Then
        assertTrue(thrown.getMessage().contains("No rate exchange found in database."));

        // When
        thrown = assertThrows(
                RateExchangeNotFoundException.class,
                () -> controller.getRateExchanges(),
                "Expected getRateExchanges() to throw, but it didn't"
        );

        // Then
        assertTrue(thrown.getMessage().contains("No rate exchange found in database."));
    }
}
