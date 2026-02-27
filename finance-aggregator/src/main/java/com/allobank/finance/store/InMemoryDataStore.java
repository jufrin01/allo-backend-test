package com.allobank.finance.store;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class InMemoryDataStore {

    private final AtomicReference<Map<String, Object>> cacheReference = new AtomicReference<>(Map.of());

    public void initializeData(Map<String, Object> fetchedData) {
        this.cacheReference.set(Map.copyOf(fetchedData));
    }

    public Object getData(String resourceType) {
        return cacheReference.get().get(resourceType);
    }
}