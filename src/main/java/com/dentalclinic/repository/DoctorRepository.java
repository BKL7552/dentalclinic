package com.dentalclinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dentalclinic.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByEmail(String email);
    Optional<Doctor> findByUserId(Long userId);
}
