package com.mrakin.service;

import com.mrakin.model.QueryTerm;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MatcherService {

    public boolean matches(String alertText, QueryTerm term) {
        if (alertText == null || term.getText() == null) {
            return false;
        }

        String normalizedAlert = alertText.toLowerCase().replaceAll("\\s+", " ");
        String normalizedTerm = term.getText().toLowerCase().replaceAll("\\s+", " ");
        String[] termParts = normalizedTerm.split(" ");

        if (term.isKeepOrder()) {
            // Если keepOrder=true, части должны идти подряд без разделяющих токенов.
            // В ТЗ: "consecutively without any separating tokens"
            // Мы можем просто искать подстроку, где пробелы нормализованы или игнорируются?
            // "IG Metall" -> "ig metall"
            return normalizedAlert.contains(normalizedTerm);
        } else {
            // Если keepOrder=false, части могут быть в любом порядке и в любом количестве где угодно в тексте.
            return Arrays.stream(termParts).allMatch(normalizedAlert::contains);
        }
    }
}
