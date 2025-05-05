/**
 * Repository interface for Dog data.
 */ 
package com.barkybarbers.barkybarbers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.barkybarbers.barkybarbers.model.Dog;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    List<Dog> findByUserId(Long userId);
}
