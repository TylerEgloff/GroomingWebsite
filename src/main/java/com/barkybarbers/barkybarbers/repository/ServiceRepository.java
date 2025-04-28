/**
 * Repository interface for Appointment data.
 * 
 * Provides Appointment entity with CRUD operations to send and recieve queries.
 */

package com.barkybarbers.barkybarbers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barkybarbers.barkybarbers.model.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
