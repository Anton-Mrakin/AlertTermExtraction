package com.mrakin.service;

import com.mrakin.model.Alert;
import com.mrakin.model.Match;
import com.mrakin.model.QueryTerm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExtractionService {
    private static final Logger log = LoggerFactory.getLogger(ExtractionService.class);

    private final ApiClient apiClient;
    private final MatcherService matcherService;

    public ExtractionService(ApiClient apiClient, MatcherService matcherService) {
        this.apiClient = apiClient;
        this.matcherService = matcherService;
    }

    public Set<Match> runExtraction(int iterations) {
        log.info("Fetching query terms...");
        List<QueryTerm> queryTerms = apiClient.getQueryTerms();
        log.info("Found {} query terms.", queryTerms.size());

        Set<Match> results = new HashSet<>();

        for (int i = 0; i < iterations; i++) {
            log.info("Processing batch {} of {}...", i + 1, iterations);
            List<Alert> alerts = apiClient.getAlerts();

            for (Alert alert : alerts) {
                if (alert.contents() == null) continue;
                for (QueryTerm term : queryTerms) {
                    boolean found = alert.contents().stream()
                            .filter(content -> term.language().equals(content.language()))
                            .anyMatch(content -> matcherService.matches(content.text(), term));
                    if (found) {
                        results.add(new Match(alert.id(), term.id()));
                    }
                }
            }
        }

        log.info("Extraction complete. Total unique matches: {}", results.size());
        return results;
    }
}
