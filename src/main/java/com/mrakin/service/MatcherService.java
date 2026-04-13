package com.mrakin.service;

import com.mrakin.model.QueryTerm;
import java.util.Arrays;

public class MatcherService {

    public boolean matches(String alertText, QueryTerm term) {
        if (alertText == null || term.text() == null) {
            return false;
        }

        String normalizedAlert = alertText.toLowerCase().replaceAll("\\s+", " ");
        String normalizedTerm = term.text().toLowerCase().replaceAll("\\s+", " ");
        String[] termParts = normalizedTerm.split(" ");

        if (term.keepOrder()) {
            return normalizedAlert.contains(normalizedTerm);
        } else {
            return Arrays.stream(termParts).allMatch(normalizedAlert::contains);
        }
    }
}
