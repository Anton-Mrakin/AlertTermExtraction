package com.mrakin.model;

public record Match(String alertId, int termId) {
    @Override
    public String toString() {
        return alertId + "," + termId;
    }
}
