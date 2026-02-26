package com.allobank.finance.strategy;

import com.allobank.finance.dto.FrankfurterRateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class LatestIdrRatesStrategy implements IDRDataFetcher {

    private static final String RESOURCE_TYPE = "latest_idr_rates";
    // Spread Factor unik hasil kalkulasi jufrin01
    private static final double SPREAD_FACTOR = 0.00751;

    private final RestTemplate restTemplate;

    @Autowired
    public LatestIdrRatesStrategy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean supports(String resourceType) { return RESOURCE_TYPE.equals(resourceType); }

    @Override
    public String getResourceType() { return RESOURCE_TYPE; }

    @Override
    public Object fetchAndTransformData() {
        String url = "/latest?base=IDR";
        System.out.println("Memanggil API: " + url);

        try {
            FrankfurterRateDTO responseDto = restTemplate.getForObject(url, FrankfurterRateDTO.class);

            if (responseDto != null && responseDto.getRates() != null) {
                Map<String, Object> rates = responseDto.getRates();
                if (rates.containsKey("USD")) {
                    Number rateUsdNumber = (Number) rates.get("USD");
                    double rateUsd = rateUsdNumber.doubleValue();

                    double usdBuySpreadIdr = (1.0 / rateUsd) * (1.0 + SPREAD_FACTOR);
                    rates.put("USD_BuySpread_IDR", usdBuySpreadIdr);
                }
            }
            return responseDto;

        } catch (Exception e) {
            System.err.println("Gagal mengambil data latest_idr_rates: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Gagal mengambil data terbaru dari API");
            errorResponse.put("details", e.getMessage());
            return errorResponse;
        }
    }
}