package com.mrakin.service;

import com.mrakin.model.QueryTerm;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MatcherServiceTest {

    private final MatcherService matcherService = new MatcherService();

    @Test
    public void testKeepOrderTrue() {
        QueryTerm term = new QueryTerm();
        term.setText("IG Metall");
        term.setKeepOrder(true);

        assertTrue(matcherService.matches("Wolfgang Lemb, IG Metall Germany stands", term));
        assertTrue(matcherService.matches("Wolfgang Lemb, ig metall Germany stands", term));
        assertFalse(matcherService.matches("Metall IG", term));
        assertFalse(matcherService.matches("IG something Metall", term));
    }

    @Test
    public void testKeepOrderFalse() {
        QueryTerm term = new QueryTerm();
        term.setText("IG Metall");
        term.setKeepOrder(false);

        assertTrue(matcherService.matches("Wolfgang Lemb, IG Metall Germany stands", term));
        assertTrue(matcherService.matches("Metall IG", term));
        assertTrue(matcherService.matches("IG some other words Metall", term));
        assertFalse(matcherService.matches("Only IG here", term));
    }
}
