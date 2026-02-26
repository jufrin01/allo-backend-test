package com.allobank.finance.strategy;

import com.allobank.finance.dto.FrankfurterRateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class HistoricalIdrUsdStrategy implements IDRDataFetcher {

    private static final String RESOURCE_TYPE = "historical_idr_usd";
    private final RestTemplate restTemplate;

    @Autowired
    public HistoricalIdrUsdStrategy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean supports(String resourceType) { return RESOURCE_TYPE.equals(resourceType); }

    @Override
    public String getResourceType() { return RESOURCE_TYPE; }

    @Override
    public Object fetchAndTransformData() {
        String url = "/2024-01-01..2024-01-05?from=IDR&to=USD";
        System.out.println("Memanggil API: " + url);

        try {
            return restTemplate.getForObject(url, FrankfurterRateDTO.class);
        } catch (Exception e) {
            System.err.println("Gagal mengambil data historical_idr_usd: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Gagal mengambil data historis");
            errorResponse.put("details", e.getMessage());
            return errorResponse;
        }
    }
}