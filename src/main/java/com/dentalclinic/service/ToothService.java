package com.dentalclinic.service;

import com.dentalclinic.model.Tooth;
import com.dentalclinic.repository.ToothRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToothService {
    @Autowired
    private ToothRepository toothRepository;

    public List<Tooth> getAllTeeth() {
        return toothRepository.findAll();
    }

    public Tooth getToothById(Long id) {
        return toothRepository.findById(id).orElseThrow(() -> new RuntimeException("Tooth not found"));
    }

    public Tooth saveTooth(Tooth tooth) {
        return toothRepository.save(tooth);
    }

    public void deleteTooth(Long id) {
        toothRepository.deleteById(id);
    }

    public List<Tooth> getTeethByTreatmentSheetId(Long treatmentSheetId) {
        return toothRepository.findByTreatmentSheetId(treatmentSheetId);
    }
}