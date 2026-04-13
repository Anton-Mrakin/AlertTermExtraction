package com.mrakin;

import com.mrakin.model.Match;
import com.mrakin.service.ExtractionService;
import com.mrakin.service.HttpApiClient;
import com.mrakin.service.MatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String apiKey = System.getProperty("apiKey", System.getenv("API_KEY"));
        int iterations = Integer.parseInt(System.getProperty("iterations", "100"));

        if (apiKey == null || apiKey.isEmpty()) {
            log.error("API key is required. Provide it via -DapiKey or API_KEY environment variable.");
            System.exit(1);
        }

        try {
            Set<Match> matches = new ExtractionService(new HttpApiClient(apiKey), new MatcherService())
                    .runExtraction(iterations);

            log.info("Matches (alertId,termId):");
            matches.forEach(m -> log.info("  {}", m));

        } catch (Exception e) {
            log.error("Error during extraction: {}", e.getMessage(), e);
            System.exit(1);
        }
    }
}
