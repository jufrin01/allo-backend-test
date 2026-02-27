package com.allobank.finance.strategy;

import com.allobank.finance.dto.FrankfurterRateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class HistoricalIdrUsdStrategy implements IDRDataFetcher {
    private static final String RESOURCE_TYPE = "historical_idr_usd";
    private final RestTemplate restTemplate;

    @Override
    public boolean supports(String resourceType) { return RESOURCE_TYPE.equals(resourceType); }

    @Override
    public String getResourceType() { return RESOURCE_TYPE; }

    @Override
    public Object fetchAndTransformData() {
       log.info("Memanggil API: /2024-01-01..2024-01-05?from=IDR&to=USD");
        try {
            return restTemplate.getForObject("/2024-01-01..2024-01-05?from=IDR&to=USD", FrankfurterRateDTO.class);
        } catch (Exception e) {
            log.error("Gagal mengambil data historical_idr_usd: " + e.getMessage());
            return null;
        }
    }
}