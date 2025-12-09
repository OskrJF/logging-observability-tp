package com.logging.tp.service;

import com.logging.tp.exception.*;
import com.logging.tp.model.*;
import com.logging.tp.logging.StructuredLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreService {
    // Question 2: Using SLF4J
    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);
    private Map<String, Product> repository = new HashMap<>();

    // Question 3: Logging for User Profiling
    // We categorize actions into "READ" or "WRITE" and track "PRICE" for high spenders.

    public List<Product> getAllProducts(User user) {
        // Log: READ operation
        logger.info(StructuredLog.create(user.getId(), "READ", "getAllProducts", 0.0));
        return new ArrayList<>(repository.values());
    }

    public Product getProduct(User user, String id) {
        if (!repository.containsKey(id)) {
            throw new ProductNotFoundException(id);
        }
        Product p = repository.get(id);
        
        // Log: READ operation, include Price to detect "High Spenders"
        logger.info(StructuredLog.create(user.getId(), "READ", "getProduct", p.getPrice()));
        return p;
    }

    public void addProduct(User user, Product product) {
        if (repository.containsKey(product.getId())) {
            throw new ProductAlreadyExistsException(product.getId());
        }
        repository.put(product.getId(), product);
        
        // Log: WRITE operation
        logger.info(StructuredLog.create(user.getId(), "WRITE", "addProduct", product.getPrice()));
    }

    public void deleteProduct(User user, String id) {
        if (!repository.containsKey(id)) {
            throw new ProductNotFoundException(id);
        }
        repository.remove(id);
        
        // Log: WRITE operation
        logger.info(StructuredLog.create(user.getId(), "WRITE", "deleteProduct", 0.0));
    }

    public void updateProduct(User user, String id, double newPrice) {
        if (!repository.containsKey(id)) {
            throw new ProductNotFoundException(id);
        }
        Product p = repository.get(id);
        p.setPrice(newPrice);
        
        // Log: WRITE operation
        logger.info(StructuredLog.create(user.getId(), "WRITE", "updateProduct", newPrice));
    }
}