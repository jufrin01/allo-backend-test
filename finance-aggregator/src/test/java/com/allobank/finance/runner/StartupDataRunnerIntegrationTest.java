package com.allobank.finance.runner;

import com.allobank.finance.store.InMemoryDataStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class StartupDataRunnerIntegrationTest {

    @Autowired
    private InMemoryDataStore dataStore;

    @Test
    void testDataIsLoadedIntoMemoryOnStartup() {
        assertNotNull(dataStore.getData("latest_idr_rates"), "Data latest rates harus dimuat");
        assertNotNull(dataStore.getData("historical_idr_usd"), "Data historical harus dimuat");
        assertNotNull(dataStore.getData("supported_currencies"), "Data currencies harus dimuat");
    }
}