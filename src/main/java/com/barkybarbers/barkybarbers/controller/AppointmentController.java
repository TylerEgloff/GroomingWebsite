/**
 * REST controller for appointment requests.
 *
 * Receives appointment DTO, builds Appointment object, and provides endpoints for CRUD operations.
 */
package com.barkybarbers.barkybarbers.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barkybarbers.barkybarbers.model.Appointment;
import com.barkybarbers.barkybarbers.model.AppointmentDTO;
import com.barkybarbers.barkybarbers.model.Service;
import com.barkybarbers.barkybarbers.repository.AppointmentRepository;
import com.barkybarbers.barkybarbers.repository.ServiceRepository;


@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;

    public AppointmentController(AppointmentRepository appointmentRepository, ServiceRepository serviceRepository) {
        this.appointmentRepository = appointmentRepository;
        this.serviceRepository = serviceRepository;
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody AppointmentDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setCustomerName(dto.getCustomerName());
        appointment.setDogBreed(dto.getDogBreed());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());

        Set<Service> services = new HashSet<>(serviceRepository.findAllById(dto.getServiceIds()));
        appointment.setServices(services);

        return appointmentRepository.save(appointment);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTO dto) {
        Optional<Appointment> optional = appointmentRepository.findById(id);
        if (optional.isEmpty()) throw new RuntimeException("Appointment not found");
        Appointment appointment = optional.get();

        appointment.setCustomerName(dto.getCustomerName());
        appointment.setDogBreed(dto.getDogBreed());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());

        Set<Service> services = new HashSet<>(serviceRepository.findAllById(dto.getServiceIds()));
        appointment.setServices(services);

        return appointmentRepository.save(appointment);
    }

}
