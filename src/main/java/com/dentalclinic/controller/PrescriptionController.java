package com.dentalclinic.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dentalclinic.model.Doctor;
import com.dentalclinic.model.MedicalRecord;

import com.dentalclinic.model.Prescription;
import com.dentalclinic.model.User;
import com.dentalclinic.repository.DoctorRepository;
import com.dentalclinic.repository.UserRepository;

import com.dentalclinic.service.MedicalRecordService;

import com.dentalclinic.service.PrescriptionService;

@Controller
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/doctor/prescriptions")
    public String listPrescription(Model model) {
    // Récupération de l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        Long userId = userOptional.get().getId();

        // Récupération du docteur associé à l'utilisateur
        Optional<Doctor> doctorOptional = doctorRepository.findByUserId(userId);
        if (doctorOptional.isEmpty()) {
            throw new RuntimeException("Docteur non trouvé");
        }
        Long doctorId = doctorOptional.get().getId();

        // Récupération des dossiers médicaux associés au docteur
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByDoctorId(doctorId);

        // Collecte de toutes les prescriptions à partir des dossiers médicaux
        List<Prescription> prescriptions = new ArrayList<>();
        for (MedicalRecord medicalRecord : medicalRecords) {
            prescriptions.addAll(medicalRecord.getPrescriptions());
        }

        // Ajout des prescriptions au modèle
        model.addAttribute("prescriptions", prescriptions);

        return "doctor/prescriptions";
    }


    @GetMapping("/doctor/prescriptions/new")
    public String showPrescriptionForm(@RequestParam("medicalRecordId") Long medicalRecordId, Model model) {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(medicalRecordId);
        if (medicalRecordId == null) {
            throw new IllegalArgumentException("Dossier medical non trouvé pour l'ID : " + medicalRecordId);
        }
        
        Prescription prescription = new Prescription();
        prescription.setMedicalRecord(medicalRecord);
        model.addAttribute("prescription", prescription);
        model.addAttribute("medicalRecord", medicalRecord);
        return "doctor/prescription-form";
    }

    @PostMapping("/prescriptions/save")
    public String saveMedicalRecord(@ModelAttribute("prescription") Prescription prescription,
                                    @RequestParam("medicalRecordId") Long medicalRecordId) {
        prescriptionService.savePrescription(prescription, medicalRecordId);
        return "redirect:/doctor/medical-records/details/" + medicalRecordId;
    }

    @GetMapping("/doctor/prescriptions/edit/{id}")
    public String editPresciptionForm(@PathVariable Long id, Model model) {
        Prescription prescription = prescriptionService.getPrescriptionById(id);
        MedicalRecord medicalRecord =prescription.getMedicalRecord();
        model.addAttribute("prescription", prescription);
        model.addAttribute("medicalRecord", medicalRecord);
        return "doctor/prescription-form";
    }

    @GetMapping("/doctor/prescriptions/delete/{id}")
    public String deletePrescription(@PathVariable Long id) {
        Prescription prescription = prescriptionService.getPrescriptionById(id);
        Long medicalRecordId =prescription.getMedicalRecord().getId();
        prescriptionService.deletePrescription(id);
        return "redirect:/doctor/medical-records/details/" + medicalRecordId;
    }
}
