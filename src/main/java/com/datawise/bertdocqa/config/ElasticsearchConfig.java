package com.datawise.bertdocqa.config;

import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.elasticsearch.ElasticsearchEmbeddingStore;
import dev.langchain4j.data.segment.TextSegment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {
    @Value("${elasticsearch.server.url}")
    private String serverUrl;

    @Value("${elasticsearch.index.name}")
    private String indexName;

    @Value("${elasticsearch.dimension}")
    private int dimension;

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return ElasticsearchEmbeddingStore.builder()
                .serverUrl(serverUrl)
                .indexName(indexName)
                .dimension(dimension)
                .build();
    }
}
