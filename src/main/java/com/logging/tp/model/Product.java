package com.logging.tp.model;

import java.time.LocalDate;

public class Product {
    private String id;
    private String name;
    private double price;
    private LocalDate expirationDate;
    
    public Product() {
    }
    
    public Product(String id, String name, double price, LocalDate expirationDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.expirationDate = expirationDate;
    }
    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}