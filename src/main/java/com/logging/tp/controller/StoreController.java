package com.logging.tp.controller;

import com.logging.tp.model.Product;
import com.logging.tp.model.User;
import com.logging.tp.service.StoreService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // Allows the React Frontend to connect
public class StoreController {

    private final StoreService service = new StoreService();
    // A placeholder user for web requests
    private final User webUser = new User("WebUser", "Frontend Client", 25, "web@example.com");

    @GetMapping("/products")
    public List<Product> getProducts() {
        return service.getAllProducts(webUser);
    }

    @PostMapping("/products")
    public void addProduct(@RequestBody Product product) {
        service.addProduct(webUser, product);
    }
}