package com.dentalclinic.repository;

import com.dentalclinic.model.Tooth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToothRepository extends JpaRepository<Tooth, Long> {
    // Trouver toutes les dents associées à une fiche de traitement via les traitements
    @Query("SELECT t FROM Tooth t JOIN t.treatments tr WHERE tr.treatmentSheet.id = :treatmentSheetId")
    List<Tooth> findByTreatmentSheetId(Long treatmentSheetId);
}