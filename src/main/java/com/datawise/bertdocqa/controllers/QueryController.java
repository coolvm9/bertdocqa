package com.datawise.bertdocqa.controllers;

import com.datawise.bertdocqa.dto.QueryRequest;
import com.datawise.bertdocqa.dto.QueryResponse;
import com.datawise.bertdocqa.services.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QueryController {

    private static final Logger logger = LoggerFactory.getLogger(QueryController.class);

    private final QueryService queryService;

    @Autowired
    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @PostMapping("/query")
    public ResponseEntity<?> processQuery(@RequestBody QueryRequest queryRequest){
        try {
            QueryResponse response = queryService.processQuery(queryRequest.getQuery(), queryRequest.getResultCount());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Failed to process query", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing query: " + e.getMessage());
        }
    }

    @GetMapping("/query/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Service is up and running!");
    }
}
