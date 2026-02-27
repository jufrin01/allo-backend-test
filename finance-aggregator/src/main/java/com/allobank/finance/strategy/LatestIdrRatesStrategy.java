package com.allobank.finance.strategy;

import com.allobank.finance.dto.FrankfurterRateDTO;
import com.allobank.finance.util.SpreadCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@Component
public class LatestIdrRatesStrategy implements IDRDataFetcher {
    private static final String RESOURCE_TYPE = "latest_idr_rates";

    private final RestTemplate restTemplate;
    private final double spreadFactor;

    public LatestIdrRatesStrategy(RestTemplate restTemplate,
                                  @Value("${allobank.developer.username}") String username) {
        this.restTemplate = restTemplate;
        this.spreadFactor = SpreadCalculator.calculateSpread(username);
    }

    @Override
    public boolean supports(String resourceType) {
        return RESOURCE_TYPE.equals(resourceType);
    }

    @Override
    public String getResourceType() {
        return RESOURCE_TYPE;
    }

    @Override
    public Object fetchAndTransformData() {
        log.info("Memanggil API: /latest?base=IDR");

        try {
            FrankfurterRateDTO responseDto = restTemplate.getForObject("/latest?base=IDR", FrankfurterRateDTO.class);

            if (responseDto != null && responseDto.getRates() != null) {
                Map<String, Object> rates = responseDto.getRates();

                if (rates.containsKey("USD")) {
                    Number rateUsdNumber = (Number) rates.get("USD");
                    double rateUsd = rateUsdNumber.doubleValue();
                    BigDecimal plainUsd = new BigDecimal(rateUsdNumber.toString());
                    rates.put("USD", plainUsd.toPlainString());

                    double usdBuySpreadIdr = (1.0 / rateUsd) * (1.0 + spreadFactor);
                    rates.put("USD_BuySpread_IDR", usdBuySpreadIdr);
                }
            }

            return responseDto;

        } catch (Exception e) {

            log.error("Gagal mengambil data latest_idr_rates: {}", e.getMessage());
            return null;
        }
    }
}