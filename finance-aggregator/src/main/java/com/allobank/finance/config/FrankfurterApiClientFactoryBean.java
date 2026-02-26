package com.allobank.finance.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Component
public class FrankfurterApiClientFactoryBean implements FactoryBean<RestTemplate> {

    @Value("${frankfurter.api.base-url}")
    private String baseUrl;

    @Override
    public RestTemplate getObject() {
        return new RestTemplateBuilder()
                .rootUri(baseUrl)
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return RestTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}