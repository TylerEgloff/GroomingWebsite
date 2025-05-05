/**
 * Data Transfer Object (DTO) for transferring appointment data. Allows creation and updating of appointments using IDs instead of objects.
 */

package com.barkybarbers.barkybarbers.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentDTO {
    private Long userId;
    private Long dogId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
    private String notes;
    private List<Long> serviceIds;

    // Getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Long getDogId() { return dogId; }
    public void setDogId(Long dogId) { this.dogId = dogId; }
    
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }
    
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public List<Long> getServiceIds() { return serviceIds; }
    public void setServiceIds(List<Long> serviceIds) { this.serviceIds = serviceIds; }
}
