/**
 * REST controller for service requests
 *
 * Connects JavaScript form submission with JPA 'Service' Entity.
 */
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
@RequestMapping("/services")
public class ServiceController {

    private final ServiceRepository serviceRepository;

    public ServiceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @GetMapping
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    @PostMapping
    public Service createService(@RequestBody Service service) {
        return serviceRepository.save(service);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Long id) {
        serviceRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Service updateService(@PathVariable Long id, @RequestBody Service updatedService) {
        Service service = serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not found"));

        service.setName(updatedService.getName());
        service.setDescription(updatedService.getDescription());
        service.setPrice(updatedService.getPrice());

        return serviceRepository.save(service);
    }

}
