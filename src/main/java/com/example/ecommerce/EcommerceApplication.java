package com.example.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class EcommerceApplication {

    // 🔥 Read Mongo URI from properties
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    // 🔍 Print it after app starts
    @PostConstruct
    public void printMongoUri() {
        System.out.println("MONGO URI: " + mongoUri);
    }

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
}