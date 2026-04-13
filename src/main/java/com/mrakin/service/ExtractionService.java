package com.mrakin.service;

import com.mrakin.model.Alert;
import com.mrakin.model.QueryTerm;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExtractionService {
    private final ApiClient apiClient;
    private final MatcherService matcherService;

    public ExtractionService(ApiClient apiClient, MatcherService matcherService) {
        this.apiClient = apiClient;
        this.matcherService = matcherService;
    }

    public Set<String> runExtraction(int iterations) throws IOException, InterruptedException {
        System.out.println("Fetching query terms...");
        List<QueryTerm> queryTerms = apiClient.getQueryTerms();
        System.out.println("Found " + queryTerms.size() + " query terms.");

        Set<String> results = new HashSet<>();

        for (int i = 0; i < iterations; i++) {
            System.out.println("Processing batch " + (i + 1) + " of " + iterations + "...");
            List<Alert> alerts = apiClient.getAlerts();

            for (Alert alert : alerts) {
                for (QueryTerm term : queryTerms) {
                    if (alert.contents() != null) {
                        boolean found = alert.contents().stream()
                                .anyMatch(content -> matcherService.matches(content.text(), term));

                        if (found) {
                            results.add(alert.id() + "," + term.id());
                        }
                    }
                }
            }
        }
        return results;
    }
}
