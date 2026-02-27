package com.allobank.finance.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SupportedCurrenciesStrategy implements IDRDataFetcher {

    private static final String RESOURCE_TYPE = "supported_currencies";
    private final RestTemplate restTemplate;

    @Override
    public boolean supports(String resourceType) { return RESOURCE_TYPE.equals(resourceType); }

    @Override
    public String getResourceType() { return RESOURCE_TYPE; }

    @Override
    public Object fetchAndTransformData() {
        System.out.println("Memanggil API: /currencies");
        try {
            return restTemplate.getForObject("/currencies", Map.class);
        } catch (Exception e) {
            System.err.println("Gagal mengambil data supported_currencies: " + e.getMessage());
            return null;
        }
    }
}