package com.allobank.finance.runner;

import com.allobank.finance.store.InMemoryDataStore;
import com.allobank.finance.strategy.IDRDataFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartupDataRunner implements ApplicationRunner {

    private final List<IDRDataFetcher> strategies;
    private final InMemoryDataStore dataStore;

    @Override
    public void run(ApplicationArguments args) {
        log.info(" Mengambil data dari Frankfurter API saat startup...");
        Map<String, Object> temporaryMap = new HashMap<>();

        for (IDRDataFetcher strategy : strategies) {
            Object fetchedData = strategy.fetchAndTransformData();

            if (fetchedData != null) {
                temporaryMap.put(strategy.getResourceType(), fetchedData);
            } else {
                log.error("Data untuk '" + strategy.getResourceType() + "' kosong/gagal diambil dari API.");
            }
        }

        dataStore.initializeData(temporaryMap);
    }
}