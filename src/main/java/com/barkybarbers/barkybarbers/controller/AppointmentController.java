/**
 * REST controller for appointment requests.
*/
package com.barkybarbers.barkybarbers.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.barkybarbers.barkybarbers.model.Appointment;
import com.barkybarbers.barkybarbers.model.AppointmentDTO;
import com.barkybarbers.barkybarbers.model.Dog;
import com.barkybarbers.barkybarbers.model.Service;
import com.barkybarbers.barkybarbers.model.User;
import com.barkybarbers.barkybarbers.repository.AppointmentRepository;
import com.barkybarbers.barkybarbers.repository.DogRepository;
import com.barkybarbers.barkybarbers.repository.ServiceRepository;
import com.barkybarbers.barkybarbers.repository.UserRepository;

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DogRepository dogRepository;
    
    @Autowired
    private ServiceRepository serviceRepository;

    // Get all appointments
    @GetMapping("/api/appointments")
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    // Get all appointments of a given user ID
    @GetMapping("/api/appointments/user/{userId}")
    public List<Appointment> getAppointmentsByUserId(@PathVariable Long userId) {
        return appointmentRepository.findByUserId(userId);
    }
    
    // Get all appointments of a given dog ID
    @GetMapping("/api/appointments/dog/{dogId}")
    public List<Appointment> getAppointmentsByDogId(@PathVariable Long dogId) {
        return appointmentRepository.findByDogId(dogId);
    }

    // Get an appointment given its ID
    @GetMapping("/api/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        return appointment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new appointment
    @PostMapping("/api/appointments")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentDTO dto) {
        try {
            // Validate user
            Optional<User> userOpt = userRepository.findById(dto.getUserId());
            if (userOpt.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "User not found");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Validate dog
            Optional<Dog> dogOpt = dogRepository.findById(dto.getDogId());
            if (dogOpt.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Dog not found");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Validate dog belongs to user
            Dog dog = dogOpt.get();
            if (!dog.getUser().getId().equals(dto.getUserId())) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Dog does not belong to this user");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Create appointment
            Appointment appointment = new Appointment();
            appointment.setUser(userOpt.get());
            appointment.setDog(dog);
            appointment.setAppointmentDate(dto.getAppointmentDate());
            appointment.setAppointmentTime(dto.getAppointmentTime());
            appointment.setStatus(dto.getStatus() != null ? dto.getStatus() : "SCHEDULED");
            appointment.setNotes(dto.getNotes());
            appointment.setCreatedAt(LocalDateTime.now());
            appointment.setUpdatedAt(LocalDateTime.now());
            
            // Add services
            if (dto.getServiceIds() != null && !dto.getServiceIds().isEmpty()) {
                Set<Service> services = new HashSet<>(serviceRepository.findAllById(dto.getServiceIds()));
                appointment.setServices(services);
            }
            
            Appointment savedAppointment = appointmentRepository.save(appointment);
            return ResponseEntity.ok(savedAppointment);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Update an appointment given its ID
    @PutMapping("/api/appointments/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTO dto) {
        try {
            Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
            if (optionalAppointment.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Appointment appointment = optionalAppointment.get();
            
            // Update dog
            if (dto.getDogId() != null) {
                Optional<Dog> dogOpt = dogRepository.findById(dto.getDogId());
                if (dogOpt.isEmpty()) {
                    Map<String, String> response = new HashMap<>();
                    response.put("error", "Dog not found");
                    return ResponseEntity.badRequest().body(response);
                }
                
                // Validate dog belongs to user
                Dog dog = dogOpt.get();
                if (!dog.getUser().getId().equals(appointment.getUser().getId())) {
                    Map<String, String> response = new HashMap<>();
                    response.put("error", "Dog does not belong to this user");
                    return ResponseEntity.badRequest().body(response);
                }
                
                appointment.setDog(dog);
            }
            
            // Update other fields
            if (dto.getAppointmentDate() != null) {
                appointment.setAppointmentDate(dto.getAppointmentDate());
            }
            if (dto.getAppointmentTime() != null) {
                appointment.setAppointmentTime(dto.getAppointmentTime());
            }
            if (dto.getStatus() != null) {
                appointment.setStatus(dto.getStatus());
            }
            if (dto.getNotes() != null) {
                appointment.setNotes(dto.getNotes());
            }
            
            // Update services
            if (dto.getServiceIds() != null) {
                Set<Service> services = new HashSet<>(serviceRepository.findAllById(dto.getServiceIds()));
                appointment.setServices(services);
            }
            
            appointment.setUpdatedAt(LocalDateTime.now());
            
            Appointment updatedAppointment = appointmentRepository.save(appointment);
            return ResponseEntity.ok(updatedAppointment);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Delete an appointment given its ID
    @DeleteMapping("/api/appointments/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        try {
            Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
            if (optionalAppointment.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            appointmentRepository.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Appointment deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
