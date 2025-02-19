package com.dentalclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dentalclinic.model.TreatmentSheet;

public interface TreatmentSheetRepository extends JpaRepository<TreatmentSheet, Long> {
    List<TreatmentSheet> findByPatientId(Long patientId);
    Optional<TreatmentSheet> findByInvoiceId(Long invoiceId);

    /*
     * @Query("SELECT ts FROM TreatmentSheet ts WHERE ts.doctor.id = :doctorId AND ts.status = 'ONGOING'")
        List<TreatmentSheet> findOngoingTreatmentSheetsByDoctorId(Long doctorId);
     */
}