package com.datawise.bertdocqa.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RestClient {
    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);
    private final RestTemplate restTemplate;
    @Value("${bert.service.url}")
    private String bertServerUrl;

    @Autowired
    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAnswer(String question, List<String> paragraphs) {
        Map<String, Object> request = new HashMap<>();
        request.put("question", question);
        request.put("paragraphs", paragraphs);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(bertServerUrl, request, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            logger.error("Client error while making request to BERT service", e);
            throw new RuntimeException("Client error: " + e.getResponseBodyAsString(), e);
        } catch (RestClientResponseException e) {
            logger.error("Error while making request to BERT service", e);
            throw new RuntimeException("Error from BERT service: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            logger.error("Unexpected error while making request to BERT service", e);
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }
}
