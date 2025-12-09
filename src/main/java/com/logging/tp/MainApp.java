package com.logging.tp;

import com.logging.tp.model.*;
import com.logging.tp.service.StoreService;
import com.logging.tp.analysis.LogAnalyzer;

import java.time.LocalDate;

public class MainApp {
    public static void main(String[] args) {
        StoreService service = new StoreService();

        // 1. Setup Data
        Product p1 = new Product("P01", "Laptop", 1500.00, LocalDate.now().plusYears(2));
        Product p2 = new Product("P02", "Mouse", 20.00, LocalDate.now().plusYears(1));
        Product p3 = new Product("P03", "Server", 5000.00, LocalDate.now().plusYears(5));

        // 2. Define Users (Question 4)
        User aliceReader = new User("U1", "Alice", 25, "alice@test.com");
        User bobWriter = new User("U2", "Bob", 30, "bob@test.com");
        User richCharlie = new User("U3", "Charlie", 40, "charlie@test.com");

        // 3. Execute Scenarios (Question 4)
        System.out.println("--- Simulating Scenarios ---");

        // Scenario A: Alice mainly reads (Reader Profile)
        try {
            service.addProduct(bobWriter, p1); // Bob adds data for Alice to read
            service.addProduct(bobWriter, p2);
            service.addProduct(bobWriter, p3);
            
            service.getAllProducts(aliceReader);
            service.getProduct(aliceReader, "P01");
            service.getProduct(aliceReader, "P02");
            service.getProduct(aliceReader, "P02"); // Repeated read
        } catch (Exception e) { e.printStackTrace(); }

        // Scenario B: Bob mainly writes (Writer Profile)
        try {
            service.updateProduct(bobWriter, "P02", 25.00);
            service.deleteProduct(bobWriter, "P02");
            service.addProduct(bobWriter, new Product("P04", "Keyboard", 50.0, LocalDate.now()));
        } catch (Exception e) { e.printStackTrace(); }

        // Scenario C: Charlie looks for expensive items (High Value Profile)
        try {
            service.getProduct(richCharlie, "P01"); // 1500.00
            service.getProduct(richCharlie, "P03"); // 5000.00
        } catch (Exception e) { e.printStackTrace(); }

        System.out.println("--- Simulation Finished. Logs written to application.log ---");
        
        // 4. Run Analysis (Question 5)
        System.out.println("\n--- Generating User Profiles ---");
        LogAnalyzer.analyzeLogs("application.log");
    }
}