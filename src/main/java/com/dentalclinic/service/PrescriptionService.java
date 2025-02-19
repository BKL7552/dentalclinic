package com.dentalclinic.service;

import com.dentalclinic.model.MedicalRecord;
import com.dentalclinic.model.Prescription;
import com.dentalclinic.repository.MedicalRecordRepository;
import com.dentalclinic.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;


    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id).orElseThrow(() -> new RuntimeException("Prescription not found"));
    }

    public Prescription savePrescription(Prescription prescription, Long medicalRecordId) {
        MedicalRecord medicalRecord= medicalRecordRepository.findById(medicalRecordId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        prescription.setMedicalRecord(medicalRecord);
        return prescriptionRepository.save(prescription);
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }

    public List<Prescription> getPrescriptionsByMedicalRecordId(Long medicalRecordId) {
        return prescriptionRepository.findByMedicalRecordId(medicalRecordId);
    }
}