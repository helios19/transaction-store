package com.wex.ratexchg.service;

import com.wex.ratexchg.model.RateExchange;
import com.wex.ratexchg.repository.RateExchangeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wex.common.ClassTestUtils.RATEEXCHANGE_SAMPLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class RateExchangeServiceTest {
    @Mock
    private RateExchangeRepository rateExchangeRepository;

    @InjectMocks
    private RateExchangeServiceImpl rateExchangeService;

    private RateExchange rateExchange;

    @BeforeEach
    public void setup() {
        rateExchange = RATEEXCHANGE_SAMPLE;
    }

    @Test
    public void givenRateExchangeWhenSavedThenReturnRateExchangeObject() {
        // Given
        given(rateExchangeRepository.save(rateExchange)).willReturn(rateExchange);

        // When
        RateExchange r = rateExchangeService.save(rateExchange);

        // Then
        assertThat(r).isNotNull();
        verify(rateExchangeRepository, times(1)).save(any(RateExchange.class));
        verifyNoMoreInteractions(rateExchangeRepository);
    }
}
