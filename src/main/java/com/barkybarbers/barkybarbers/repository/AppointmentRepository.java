package com.barkybarbers.barkybarbers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barkybarbers.barkybarbers.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
