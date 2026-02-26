package com.allobank.finance.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class SupportedCurrenciesStrategy implements IDRDataFetcher {

    private static final String RESOURCE_TYPE = "supported_currencies";
    private final RestTemplate restTemplate;

    @Autowired
    public SupportedCurrenciesStrategy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean supports(String resourceType) { return RESOURCE_TYPE.equals(resourceType); }

    @Override
    public String getResourceType() { return RESOURCE_TYPE; }

    @Override
    public Object fetchAndTransformData() {
        String url = "/currencies";
        System.out.println("Memanggil API: " + url);

        try {
            return restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            System.err.println("Gagal mengambil data supported_currencies: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Gagal mengambil daftar mata uang");
            errorResponse.put("details", e.getMessage());
            return errorResponse;
        }
    }
}