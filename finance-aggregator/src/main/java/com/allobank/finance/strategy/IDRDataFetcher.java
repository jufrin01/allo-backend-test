package com.allobank.finance.strategy;


public interface IDRDataFetcher {
    boolean supports(String resourceType);
    String getResourceType();
    Object fetchAndTransformData();
}