package com.logging.tp.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class StructuredLog {
    private static final ObjectMapper mapper = new ObjectMapper();

    // The "LPS" structure (Log Printing Statement) as defined in slides
    public static String create(String userId, String operationType, String context, double valueMetric) {
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("timestamp", LocalDateTime.now().toString());
        logMap.put("who", userId);              // User ID
        logMap.put("what", operationType);      // READ or WRITE
        logMap.put("where", context);           // Method name or Action
        logMap.put("metric", valueMetric);      // Price (if applicable) for "High Spender" profile
        
        try {
            return mapper.writeValueAsString(logMap);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}