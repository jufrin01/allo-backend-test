package com.allobank.finance.service;

import com.allobank.finance.store.InMemoryDataStore;
import com.allobank.finance.strategy.IDRDataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinanceDataService {

    private final InMemoryDataStore dataStore;
    private final Map<String, IDRDataFetcher> strategyMap = new HashMap<>();

    @Autowired
    public FinanceDataService(InMemoryDataStore dataStore, List<IDRDataFetcher> strategies) {
        this.dataStore = dataStore;
        for (IDRDataFetcher strategy : strategies) {
            this.strategyMap.put(strategy.getResourceType(), strategy);
        }
    }

    public Object getFinanceData(String resourceType) {
        if (!strategyMap.containsKey(resourceType)) {
            return null;
        }

        return dataStore.getData(resourceType);
    }
}