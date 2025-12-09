package com.logging.tp.model;

public class User {
    private String id;
    private String name;
    private int age;
    private String email;

    public User(String id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
}
