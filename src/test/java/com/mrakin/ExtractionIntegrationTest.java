package com.mrakin;

import com.mrakin.service.ExtractionService;
import com.mrakin.service.HttpApiClient;
import com.mrakin.service.MatcherService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrakin.model.Match;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class ExtractionIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(ExtractionIntegrationTest.class);

    @Test
    void testExtractionWithRealApi() throws Exception {
        String apiKey = System.getProperty("apiKey", System.getenv("API_KEY"));
        assumeTrue(apiKey != null && !apiKey.isBlank(),
                "Skipping: provide API key via -DapiKey=... or API_KEY env var");

        Set<Match> matches = new ExtractionService(new HttpApiClient(apiKey), new MatcherService())
                .runExtraction(200);

        assertNotNull(matches);
        log.info("Integration test matches count: {}", matches.size());
        matches.forEach(m -> log.info("  {}", m));
    }
}
