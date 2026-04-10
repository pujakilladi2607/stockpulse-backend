package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // ✅ Register
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        User existing = userRepository.findByEmail(user.getEmail());

        if (existing != null) {
            return "User already exists";
        }

        userRepository.save(user);
        return "User registered successfully";
    }

    // ✅ Login
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User existing = userRepository.findByEmail(user.getEmail());

        if (existing == null) {
            return "USER_NOT_FOUND";
        }

        if (!existing.getPassword().equals(user.getPassword())) {
            return "INVALID_PASSWORD";
        }

        return "SUCCESS";
    }

    // 🔥 Create TEST USER
    @GetMapping("/create-test-user")
    public String createTestUser() {

        User user = new User();
        user.setName("Test User");
        user.setEmail("test@gmail.com");
        user.setPassword("123");
        user.setAddress("India");

        userRepository.save(user);

        return "Test user created";
    }

    // 🔥 Create ADMIN USER (FIXED)
    @GetMapping("/create-admin")
    public String createAdmin() {

        String adminEmail = "stockpulseadmin@gmail.com";

        User existing = userRepository.findByEmail(adminEmail);
        if (existing != null) {
            userRepository.delete(existing);
        }

        User user = new User();
        user.setName("Admin");
        user.setEmail(adminEmail);
        user.setPassword("123456");
        user.setAddress("India");

        userRepository.save(user);

        return "Admin created successfully";
    }
}