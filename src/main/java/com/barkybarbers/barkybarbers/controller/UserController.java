/**
 * REST controller for User requests
 * */
package com.barkybarbers.barkybarbers.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.barkybarbers.barkybarbers.model.User;
import com.barkybarbers.barkybarbers.repository.UserRepository;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    @GetMapping("/api/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a user given their ID
    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new user
    @PostMapping("/api/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            // Check if username already exists
            if (userRepository.existsByUsername(user.getUsername())) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Username already exists");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Check if email already exists
            if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Email already exists");
                return ResponseEntity.badRequest().body(response);
            }
            
            user.setCreatedAt(LocalDateTime.now());
            User savedUser = userRepository.save(user);
            
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Post login request
    @PostMapping("/api/users/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        try {
            Optional<User> userOpt = userRepository.findByUsername(loginUser.getUsername());
            
            if (userOpt.isPresent() && userOpt.get().getPassword().equals(loginUser.getPassword())) {
                return ResponseEntity.ok(userOpt.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Invalid username or password");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Update a user given ID
    @PutMapping("/api/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            User user = optionalUser.get();
            
            // Check if username is being changed and already exists
            if (!user.getUsername().equals(userDetails.getUsername()) && 
                userRepository.existsByUsername(userDetails.getUsername())) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Username already exists");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Check if email is being changed and already exists
            if (userDetails.getEmail() != null && !userDetails.getEmail().equals(user.getEmail()) && 
                userRepository.existsByEmail(userDetails.getEmail())) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Email already exists");
                return ResponseEntity.badRequest().body(response);
            }
            
            user.setUsername(userDetails.getUsername());
            if (userDetails.getEmail() != null) user.setEmail(userDetails.getEmail());
            if (userDetails.getPhone() != null) user.setPhone(userDetails.getPhone());
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                user.setPassword(userDetails.getPassword());
            }
            
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Delete a user given ID
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            if (!userRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}