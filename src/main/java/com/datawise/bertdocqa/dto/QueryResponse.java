package com.datawise.bertdocqa.dto;

import java.util.List;
public class QueryResponse {
    private String query;
    private List<String> passages;
    private String answer;

    public QueryResponse(String query, List<String> passages, String answer) {
        this.query = query;
        this.passages = passages;
        this.answer = answer;
    }

    // Getters and Setters
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getPassages() {
        return passages;
    }

    public void setPassages(List<String> passages) {
        this.passages = passages;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}