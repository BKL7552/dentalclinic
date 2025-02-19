package com.dentalclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dentalclinic.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByPatientId(Long patientId);
    List<Invoice> findByIsPaidFalse();
    Optional<Invoice> findByTreatmentSheetId(Long treatmentSheetId);
}
