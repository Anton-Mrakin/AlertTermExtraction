package com.mrakin.service;

import com.mrakin.model.Alert;
import com.mrakin.model.QueryTerm;

import java.util.List;

class StubApiClient implements ApiClient {
    private final List<QueryTerm> terms;
    private final List<Alert> alerts;

    StubApiClient(List<QueryTerm> terms, List<Alert> alerts) {
        this.terms = terms;
        this.alerts = alerts;
    }

    @Override
    public List<QueryTerm> getQueryTerms() {
        return terms;
    }

    @Override
    public List<Alert> getAlerts() {
        return alerts;
    }
}
