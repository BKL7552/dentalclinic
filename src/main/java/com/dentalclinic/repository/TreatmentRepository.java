package com.dentalclinic.repository;

import com.dentalclinic.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    // Trouver tous les traitements associés à une fiche de traitement
    List<Treatment> findByTreatmentSheetId(Long treatmentSheetId);

    // Trouver tous les traitements associés à une dent
    List<Treatment> findByToothId(Long toothId);
}