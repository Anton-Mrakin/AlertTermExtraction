package com.mrakin;

import com.mrakin.service.ApiClient;
import com.mrakin.service.ExtractionService;
import com.mrakin.service.MatcherService;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        String apiKey = System.getProperty("apiKey", System.getenv("API_KEY"));
        int iterations = Integer.parseInt(System.getProperty("iterations", "100"));

        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("API key is required. Provide it via -DapiKey or API_KEY environment variable.");
            System.exit(1);
        }

        try {
            ApiClient apiClient = new ApiClient(apiKey);
            MatcherService matcherService = new MatcherService();
            ExtractionService extractionService = new ExtractionService(apiClient, matcherService);

            Set<String> matches = extractionService.runExtraction(iterations);

            System.out.println("\nMatches (alertId,termId):");
            matches.forEach(System.out::println);
            System.out.println("\nTotal unique matches: " + matches.size());

        } catch (Exception e) {
            System.err.println("Error during extraction: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
