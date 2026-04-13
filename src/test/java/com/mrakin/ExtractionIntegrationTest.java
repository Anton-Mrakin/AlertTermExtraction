package com.mrakin;

import com.mrakin.service.ApiClient;
import com.mrakin.service.ExtractionService;
import com.mrakin.service.MatcherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExtractionIntegrationTest {

    @Test
    @EnabledIfSystemProperty(named = "apiKey", matches = ".+")
    void testExtractionWithRealApi() throws Exception {
        String apiKey = System.getProperty("apiKey");
        ApiClient apiClient = new ApiClient(apiKey);
        MatcherService matcherService = new MatcherService();
        ExtractionService extractionService = new ExtractionService(apiClient, matcherService);

        // Run just a few iterations for the test
        Set<String> matches = extractionService.runExtraction(2);

        assertNotNull(matches);
        System.out.println("Integration test matches count: " + matches.size());
    }
}
