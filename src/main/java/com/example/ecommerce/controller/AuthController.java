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

    // ✅ Register (POST)
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        User existing = userRepository.findByEmail(user.getEmail());

        if (existing != null) {
            return "User already exists";
        }

        userRepository.save(user);
        return "User registered successfully";
    }

    // ✅ Login (POST)
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

    // 🔥 Create ADMIN USER
   @GetMapping("/create-admin")
public String createAdmin() {

    // DELETE old admin if exists
    User existing = userRepository.findByEmail("admin@gmail.com");
    if (existing != null) {
        userRepository.delete(existing);
    }

    // CREATE new admin
    User user = new User();
    user.setName("Admin");
    user.setEmail("stockpulseadmin@gmail.com");   // 👉 change if needed
    user.setPassword("123456");         // 👉 change if needed
    user.setAddress("India");

    userRepository.save(user);

    return "Old admin deleted (if existed) & new admin created";
}
}