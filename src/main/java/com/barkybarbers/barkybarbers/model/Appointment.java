/**
 * Represents an appointment in the appointment registry.
 * 
 * Appointment is a JPA entity that is mapped to the 'appointments' table in the SQL database. 
 * Stores customer info, dog breed, requested service ID (foreign key to 'services' table), appointment date, and time.
 */

package com.barkybarbers.barkybarbers.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id // identifies ID as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tells JPA to follow database ID generation (currently auto-incrementing)

    private Long id;
    private String customerName;
    private String dogBreed;
    private Long serviceId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getDogBreed() { return dogBreed; }
    public void setDogBreed(String dogBreed) { this.dogBreed = dogBreed; }

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }
}
