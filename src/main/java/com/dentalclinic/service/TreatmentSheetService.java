package com.dentalclinic.service;

import com.dentalclinic.model.TreatmentSheet;
import com.dentalclinic.model.Tooth;
import com.dentalclinic.repository.TreatmentSheetRepository;
import com.dentalclinic.repository.ToothRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreatmentSheetService {
    @Autowired
    private TreatmentSheetRepository treatmentSheetRepository;

    @Autowired
    private ToothRepository toothRepository;

    public List<TreatmentSheet> getAllTreatmentSheets() {
        return treatmentSheetRepository.findAll();
    }

    public TreatmentSheet getTreatmentSheetById(Long id) {
        return treatmentSheetRepository.findById(id).orElseThrow(() -> new RuntimeException("TreatmentSheet not found"));
    }

    public TreatmentSheet saveTreatmentSheet(TreatmentSheet treatmentSheet) {
        return treatmentSheetRepository.save(treatmentSheet);
    }

    public void deleteTreatmentSheet(Long id) {
        treatmentSheetRepository.deleteById(id);
    }

    public List<Tooth> getTeethByTreatmentSheetId(Long treatmentSheetId) {
        return toothRepository.findByTreatmentSheetId(treatmentSheetId);
    }
    
    public List<TreatmentSheet> getTreatmentSheetsByPatientId(Long patientId) {
        return treatmentSheetRepository.findByPatientId(patientId);
    }
    public Optional<TreatmentSheet> getTreatmentSheetByInvoiceId(Long invoiceId) {
        return treatmentSheetRepository.findByInvoiceId(invoiceId);
    }
    /*
    public int getOngoingTreatmentSheetsByDoctorId(Long doctorId) {
        return treatmentSheetRepository.findOngoingTreatmentSheetsByDoctorId(doctorId).size();
    }
    */
}