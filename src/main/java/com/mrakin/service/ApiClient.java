package com.mrakin.service;

import com.mrakin.model.Alert;
import com.mrakin.model.QueryTerm;

import java.util.List;

public interface ApiClient {
    List<QueryTerm> getQueryTerms();
    List<Alert> getAlerts();
}
