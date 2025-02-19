package com.dentalclinic.service;

import com.dentalclinic.model.Treatment;
import com.dentalclinic.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreatmentService {
    @Autowired
    private TreatmentRepository treatmentRepository;

    public List<Treatment> getAllTreatments() {
        return treatmentRepository.findAll();
    }

    public Treatment getTreatmentById(Long id) {
        return treatmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Treatment not found"));
    }

    public Treatment saveTreatment(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }

    public void deleteTreatment(Long id) {
        treatmentRepository.deleteById(id);
    }

    public List<Treatment> getTreatmentsByTreatmentSheetId(Long treatmentSheetId) {
        return treatmentRepository.findByTreatmentSheetId(treatmentSheetId);
    }

    public List<Treatment> getTreatmentsByToothId(Long toothId) {
        return treatmentRepository.findByToothId(toothId);
    }
}