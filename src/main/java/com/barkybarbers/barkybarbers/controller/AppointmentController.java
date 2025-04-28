/**
 * REST controller for appointment requests.
 *
 * Connects JavaScript form submission with JPA 'Appointment' Entity.
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

import com.barkybarbers.barkybarbers.model.Appointment;
import com.barkybarbers.barkybarbers.repository.AppointmentRepository;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;

    public AppointmentController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment updatedAppointment) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setCustomerName(updatedAppointment.getCustomerName());
        appointment.setDogBreed(updatedAppointment.getDogBreed());
        appointment.setServiceId(updatedAppointment.getServiceId());
        appointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
        appointment.setAppointmentTime(updatedAppointment.getAppointmentTime());

        return appointmentRepository.save(appointment);
    }

}
