/**
 * Represents an appointment in the appointment registry.
 * 
 * Appointment is a JPA entity that is mapped to the 'appointments' table in the SQL database. 
 * Stores customer info, dog breed, appointment date, time, and services requested.
 */

package com.barkybarbers.barkybarbers.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id // identifies ID as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tells JPA to increment ID

    private Long id;
    private String customerName;
    private String dogBreed;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    // Many-to-many relationship with Service
    @ManyToMany
    @JoinTable(
        name = "appointment_services",
        joinColumns = @JoinColumn(name = "appointment_id"),
        inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> services;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getDogBreed() { return dogBreed; }
    public void setDogBreed(String dogBreed) { this.dogBreed = dogBreed; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }

    public Set<Service> getServices() { return services; }
    public void setServices(Set<Service> services) { this.services = services; }
}
