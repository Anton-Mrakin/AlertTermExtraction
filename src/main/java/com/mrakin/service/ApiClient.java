package com.mrakin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrakin.model.Alert;
import com.mrakin.model.QueryTerm;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiClient {
    private final String apiKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ApiClient(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<QueryTerm> getQueryTerms() throws IOException, InterruptedException {
        String url = "https://services.prewave.ai/adminInterface/api/testQueryTerm?key=" + apiKey;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to get query terms: " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), new TypeReference<List<QueryTerm>>() {});
    }

    public List<Alert> getAlerts() throws IOException, InterruptedException {
        String url = "https://services.prewave.ai/adminInterface/api/testAlerts?key=" + apiKey;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to get alerts: " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), new TypeReference<List<Alert>>() {});
    }
}
