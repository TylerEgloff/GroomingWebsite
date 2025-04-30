/**
 * Data Transfer Object (DTO) for transferring appointment data.
 * Maps JSON to this object, allowing AppointmentController to build the Appointment object.
 * This is used to allow submission of requested services just as IDs, 
 * then AppointmentController will fetch the full Service objects from the database when building an Appointment object.
 */

package com.barkybarbers.barkybarbers.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentDTO {
    private String customerName;
    private String dogBreed;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private List<Long> serviceIds;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public List<Long> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<Long> serviceIds) {
        this.serviceIds = serviceIds;
    }
}