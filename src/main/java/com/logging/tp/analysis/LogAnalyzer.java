package com.logging.tp.analysis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class LogAnalyzer {

    // Internal class to hold stats for a user
    static class UserStats {
        int readCount = 0;
        int writeCount = 0;
        double maxPriceViewed = 0.0;
    }

    public static void analyzeLogs(String logFilePath) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, UserStats> statsMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Parse JSON log line
                try {
                    JsonNode node = mapper.readTree(line);
                    String userId = node.get("who").asText();
                    String type = node.get("what").asText();
                    double metric = node.get("metric").asDouble();

                    statsMap.putIfAbsent(userId, new UserStats());
                    UserStats stats = statsMap.get(userId);

                    // Aggregation Logic
                    if ("READ".equals(type)) {
                        stats.readCount++;
                        if (metric > stats.maxPriceViewed) {
                            stats.maxPriceViewed = metric;
                        }
                    } else if ("WRITE".equals(type)) {
                        stats.writeCount++;
                    }

                } catch (Exception ignored) {
                    // Ignore non-JSON lines (if any)
                }
            }

            // Generate Profiles based on logic defined in Question 3
            System.out.println("USER PROFILES GENERATED:");
            for (Map.Entry<String, UserStats> entry : statsMap.entrySet()) {
                String user = entry.getKey();
                UserStats s = entry.getValue();
                
                System.out.print("User: " + user + " -> ");
                
                // Profile Logic
                if (s.writeCount > s.readCount) {
                    System.out.print("[WRITER PROFILE] ");
                } else {
                    System.out.print("[READER PROFILE] ");
                }
                
                if (s.maxPriceViewed > 1000.0) {
                    System.out.print("[HIGH SPENDER INTEREST]");
                }
                
                System.out.println("\n\tStats: Reads=" + s.readCount + ", Writes=" + s.writeCount + ", MaxPrice=" + s.maxPriceViewed);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}