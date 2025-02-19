package com.dentalclinic.repository;

import com.dentalclinic.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    // Trouver toutes les prescriptions associées à un dossier médical
    List<Prescription> findByMedicalRecordId(Long medicalRecordId);
}