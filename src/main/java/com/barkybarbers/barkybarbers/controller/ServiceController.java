/**
 * REST controller for service requests
 * */
package com.barkybarbers.barkybarbers.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barkybarbers.barkybarbers.model.Service;
import com.barkybarbers.barkybarbers.repository.ServiceRepository;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceRepository serviceRepository;

    public ServiceController(ServiceRepository serviceRepository) { // Constructor
        this.serviceRepository = serviceRepository;
    }

    // Get all services
    @GetMapping
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    // Create a new service
    @PostMapping
    public Service createService(@RequestBody Service service) {
        return serviceRepository.save(service);
    }

    // Delete a service given its ID
    @DeleteMapping("/api/services/{id}")
    public void deleteService(@PathVariable Long id) {
        serviceRepository.deleteById(id);
    }

    // Update a service given its ID
    @PutMapping("/api/services/{id}")
    public Service updateService(@PathVariable Long id, @RequestBody Service updatedService) {
        Service service = serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not found"));

        service.setName(updatedService.getName());
        service.setDescription(updatedService.getDescription());
        service.setPrice(updatedService.getPrice());

        return serviceRepository.save(service);
    }

}
