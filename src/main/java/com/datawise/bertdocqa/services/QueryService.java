package com.datawise.bertdocqa.services;

import com.datawise.bertdocqa.component.RestClient;
import com.datawise.bertdocqa.dto.QueryResponse;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class QueryService {

    // Assume EmbeddingStore and RestClient are properly initialized or autowired
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final RestClient restClient;
    private static final Logger logger = LoggerFactory.getLogger(QueryService.class);
    private EmbeddingModel embeddingModel;


    @Autowired
    public QueryService(EmbeddingStore<TextSegment> embeddingStore, RestClient restClient) {
        this.embeddingStore = embeddingStore;
        this.restClient = restClient;

    }
    @PostConstruct
    private void init() {
        // Initialize your embedding model here
        embeddingModel = new AllMiniLmL6V2EmbeddingModel();
    }


    public QueryResponse processQuery(String query, int resultCount) {
        try {
            Embedding queryEmbedding = embeddingModel.embed(query).content();
            List<EmbeddingMatch<TextSegment>> matches = embeddingStore.findRelevant(queryEmbedding, resultCount);
            List<String> passages = new ArrayList<>();
            for (EmbeddingMatch<TextSegment> match : matches) {
                logger.info("   :::  " + match.embedded().text());
                passages.add(match.embedded().text());
            }
            String answer = restClient.getAnswer(query, passages);
            return new QueryResponse(query, passages, answer);
        } catch (Exception e) {
            logger.error("Error processing query: " + query, e);

            // Handle the error appropriately. This might involve returning a QueryResponse indicating an error.
            // For simplicity, we're just returning null here, but you should tailor this to your application's needs.
            return new QueryResponse(query, Collections.emptyList(),"Error During Processing " +  e.getMessage()); // Consider returning a more informative response or throwing a custom exception.
        }
    }

}