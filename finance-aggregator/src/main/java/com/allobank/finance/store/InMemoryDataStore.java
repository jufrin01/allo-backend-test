package com.allobank.finance.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class InMemoryDataStore {
    private Map<String, Object> dataStore = new ConcurrentHashMap<>();

    public void initializeData(Map<String, Object> fetchedData) {
        this.dataStore = Map.copyOf(fetchedData);
        log.info("Data berhasil disimpan ke In-Memory Store dan dikunci (Immutable)!");
    }

    public Object getData(String resourceType) {
        return dataStore.get(resourceType);
    }
}