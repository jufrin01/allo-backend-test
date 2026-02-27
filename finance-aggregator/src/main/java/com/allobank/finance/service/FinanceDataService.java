package com.allobank.finance.service;

import com.allobank.finance.exception.FinanceDataNotFoundException;
import com.allobank.finance.store.InMemoryDataStore;
import com.allobank.finance.strategy.IDRDataFetcher;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FinanceDataService {

    private final InMemoryDataStore dataStore;
    private final List<IDRDataFetcher> strategies;
    private final Map<String, IDRDataFetcher> strategyMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for (IDRDataFetcher strategy : strategies) {
            this.strategyMap.put(strategy.getResourceType(), strategy);
        }
    }

    public Object getFinanceData(String resourceType) {
        if (!strategyMap.containsKey(resourceType)) {
            throw new FinanceDataNotFoundException("Tipe resource '" + resourceType + "' tidak didukung.");
        }
        return dataStore.getData(resourceType);
    }
}