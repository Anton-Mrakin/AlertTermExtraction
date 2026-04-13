package com.mrakin.service;

import com.mrakin.model.Alert;
import com.mrakin.model.Content;
import com.mrakin.model.Match;
import com.mrakin.model.QueryTerm;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtractionServiceTest {

    private static final QueryTerm EN_TERM = new QueryTerm(1, "test term", "en", true);
    private static final Alert MATCHING_EN_ALERT = new Alert("alert1",
            List.of(new Content("this is a test term matching", "text", "en")), "date", "type");

    private Set<Match> extract(ApiClient client, int iterations) {
        return new ExtractionService(client, new MatcherService()).runExtraction(iterations);
    }

    @Test
    void testMatchFound() {
        ApiClient client = new StubApiClient(List.of(EN_TERM), List.of(MATCHING_EN_ALERT));

        Set<Match> matches = extract(client, 1);

        assertEquals(1, matches.size());
        assertTrue(matches.contains(new Match("alert1", 1)));
    }

    @Test
    void testLanguageFilteringSkipsWrongLanguage() {
        QueryTerm deTerm = new QueryTerm(1, "test term", "de", true);
        ApiClient client = new StubApiClient(List.of(deTerm), List.of(MATCHING_EN_ALERT));

        assertTrue(extract(client, 1).isEmpty());
    }

    @Test
    void testDeduplicationAcrossIterations() {
        ApiClient client = new StubApiClient(List.of(EN_TERM), List.of(MATCHING_EN_ALERT));

        assertEquals(1, extract(client, 5).size());
    }
}
