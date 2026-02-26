package com.allobank.finance.strategy;

import com.allobank.finance.dto.FrankfurterRateDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LatestIdrRatesStrategyTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LatestIdrRatesStrategy strategy;

    @Test
    void testSupports() {
        assertTrue(strategy.supports("latest_idr_rates"), "Harus mendukung 'latest_idr_rates'");
        assertFalse(strategy.supports("tipe_lain"), "Tidak boleh mendukung tipe lain");
    }

    @Test
    void testFetchAndTransformData_Success() {
        FrankfurterRateDTO mockDto = new FrankfurterRateDTO();
        Map<String, Object> mockRates = new HashMap<>();
        mockRates.put("USD", 0.000064);
        mockDto.setRates(mockRates);

        when(restTemplate.getForObject("/latest?base=IDR", FrankfurterRateDTO.class)).thenReturn(mockDto);


        Object result = strategy.fetchAndTransformData();

        assertTrue(result instanceof FrankfurterRateDTO, "Hasil harus berupa FrankfurterRateDTO");
        FrankfurterRateDTO resultDto = (FrankfurterRateDTO) result;

        assertTrue(resultDto.getRates().containsKey("USD_BuySpread_IDR"), "Harus ada hasil kalkulasi spread");
        verify(restTemplate, times(1)).getForObject("/latest?base=IDR", FrankfurterRateDTO.class);
    }
}