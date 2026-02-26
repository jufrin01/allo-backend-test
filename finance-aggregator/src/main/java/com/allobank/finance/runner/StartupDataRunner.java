package com.allobank.finance.runner;

import com.allobank.finance.store.InMemoryDataStore;
import com.allobank.finance.strategy.IDRDataFetcher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StartupDataRunner implements ApplicationRunner {

    private final List<IDRDataFetcher> strategies;
    private final InMemoryDataStore dataStore;

    public StartupDataRunner(List<IDRDataFetcher> strategies, InMemoryDataStore dataStore) {
        this.strategies = strategies;
        this.dataStore = dataStore;
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("Mengambil data dari Frankfurter API saat startup...");

        Map<String, Object> temporaryMap = new HashMap<>();
        for (IDRDataFetcher strategy : strategies) {
            String key = strategy.getResourceType();
            Object resultData = strategy.fetchAndTransformData();
            temporaryMap.put(key, resultData);
        }

        dataStore.initializeData(temporaryMap);
    }
}