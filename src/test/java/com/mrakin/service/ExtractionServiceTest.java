package com.mrakin.service;

import com.mrakin.model.Alert;
import com.mrakin.model.Content;
import com.mrakin.model.QueryTerm;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtractionServiceTest {

    @Test
    void testExtractionLogicWithIterations() throws IOException, InterruptedException {
        // Manual mock for ApiClient
        ApiClient mockClient = new ApiClient("dummy") {
            private int callCount = 0;

            @Override
            public List<QueryTerm> getQueryTerms() {
                return List.of(new QueryTerm(1, "test term", "en", true));
            }

            @Override
            public List<Alert> getAlerts() {
                callCount++;
                // Return different results or same, let's return a match on first call
                if (callCount == 1) {
                    return List.of(new Alert("alert1", 
                        List.of(new Content("this is a test term matching", "text", "en")), 
                        "date", "type"));
                }
                return Collections.emptyList();
            }
        };

        MatcherService matcherService = new MatcherService();
        ExtractionService extractionService = new ExtractionService(mockClient, matcherService);

        Set<String> matches = extractionService.runExtraction(3);

        assertEquals(1, matches.size());
        assertTrue(matches.contains("alert1,1"));
    }
}
