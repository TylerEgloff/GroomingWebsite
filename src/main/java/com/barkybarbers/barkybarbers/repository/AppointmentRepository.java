/**
 * Repository interface for Appointment data.
 * 
 * Provides Appointment entity with CRUD operations to send and recieve queries.
 */

package com.barkybarbers.barkybarbers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.barkybarbers.barkybarbers.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserId(Long userId);
    List<Appointment> findByDogId(Long dogId);
}
