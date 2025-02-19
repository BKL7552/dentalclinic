package com.dentalclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dentalclinic.model.Doctor;
import com.dentalclinic.model.MedicalRecord;
import com.dentalclinic.model.Patient;
import com.dentalclinic.model.Prescription;
import com.dentalclinic.repository.DoctorRepository;
import com.dentalclinic.repository.MedicalRecordRepository;
import com.dentalclinic.repository.PatientRepository;
import com.dentalclinic.repository.PrescriptionRepository;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord getMedicalRecordById(Long id) {
        return medicalRecordRepository.findById(id).orElseThrow(() -> new RuntimeException("Medical Record not found"));
    }

    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord, Long patientId, Long doctorId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
                
        medicalRecord.setPatient(patient);
        medicalRecord.setDoctor(doctor);
        return medicalRecordRepository.save(medicalRecord);
    }

    public void deleteMedicalRecord(Long id) {
        medicalRecordRepository.deleteById(id);
    }

    public List<MedicalRecord> getMedicalRecordsByPatientId(Long patientId) {
        return medicalRecordRepository.findByPatientId(patientId);
    }
    
    public List<MedicalRecord> getMedicalRecordsByDoctorId(Long doctorId) {
        return medicalRecordRepository.findByDoctorId(doctorId);
    }

    public List<Prescription> getPrescriptionsByMedicalRecordId(Long medicalRecordId) {
        return prescriptionRepository.findByMedicalRecordId(medicalRecordId);
    }
    /*
    public int getPendingMedicalRecordsByDoctorId(Long doctorId) {
        return medicalRecordRepository.findPendingMedicalRecordsByDoctorId(doctorId).size();
    }
    */
}
