package com.logging.tp.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String id) {
        super("Product already exists: " + id);
    }
}