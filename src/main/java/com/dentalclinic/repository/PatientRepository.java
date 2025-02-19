package com.dentalclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dentalclinic.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
