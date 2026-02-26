package com.allobank.finance.store;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryDataStore {

    private Map<String, Object> dataStore = new ConcurrentHashMap<>();

    public void initializeData(Map<String, Object> fetchedData) {
        this.dataStore = Map.copyOf(fetchedData);
        System.out.println("✅ Data berhasil disimpan ke In-Memory Store dan dikunci (Immutable)!");
    }

    public Object getData(String resourceType) {
        return dataStore.get(resourceType);
    }
}