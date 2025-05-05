/**
 * REST controller for Dog requests
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

import com.barkybarbers.barkybarbers.model.Dog;
import com.barkybarbers.barkybarbers.model.User;
import com.barkybarbers.barkybarbers.repository.DogRepository;
import com.barkybarbers.barkybarbers.repository.UserRepository;

@RestController
public class DogController {

    @Autowired
    private DogRepository dogRepository;
    
    @Autowired
    private UserRepository userRepository;

    // Get all dogs
    @GetMapping("/api/dogs")
    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }
    
    // Get dogs given user ID
    @GetMapping("/api/dogs/user/{userId}")
    public List<Dog> getDogsByUserId(@PathVariable Long userId) {
        return dogRepository.findByUserId(userId);
    }

    // Get a dog given its ID
    @GetMapping("/api/dogs/{id}")
    public ResponseEntity<Dog> getDogById(@PathVariable Long id) {
        Optional<Dog> dog = dogRepository.findById(id);
        return dog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new dog
    @PostMapping("/api/dogs")
    public ResponseEntity<?> createDog(@RequestBody Dog dog) {
        try {
            Optional<User> user = userRepository.findById(dog.getUser().getId());
            if (user.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "User not found");
                return ResponseEntity.badRequest().body(response);
            }
            
            dog.setUser(user.get());
            dog.setCreatedAt(LocalDateTime.now());
            Dog savedDog = dogRepository.save(dog);
            return ResponseEntity.ok(savedDog);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Update a dog given its ID
    @PutMapping("/api/dogs/{id}")
    public ResponseEntity<?> updateDog(@PathVariable Long id, @RequestBody Dog dogDetails) {
        try {
            Optional<Dog> optionalDog = dogRepository.findById(id);
            if (optionalDog.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Dog dog = optionalDog.get();
            dog.setName(dogDetails.getName());
            dog.setBreed(dogDetails.getBreed());
            dog.setAge(dogDetails.getAge());
            dog.setNotes(dogDetails.getNotes());
            
            Dog updatedDog = dogRepository.save(dog);
            return ResponseEntity.ok(updatedDog);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Delete a dog given its ID
    @DeleteMapping("/api/dogs/{id}")
    public ResponseEntity<?> deleteDog(@PathVariable Long id) {
        try {
            Optional<Dog> optionalDog = dogRepository.findById(id);
            if (optionalDog.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            dogRepository.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Dog deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
