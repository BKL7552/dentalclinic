package com.dentalclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dentalclinic.model.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatientId(Long patientId);
    List<MedicalRecord> findByDoctorId(Long doctorId);

    /*
     * @Query("SELECT mr FROM MedicalRecord mr WHERE mr.doctor.id = :doctorId AND mr.status = 'PENDING'")
        List<MedicalRecord> findPendingMedicalRecordsByDoctorId(Long doctorId);
     */
}
