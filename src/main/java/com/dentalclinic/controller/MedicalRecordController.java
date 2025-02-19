package com.dentalclinic.controller;

import java.util.Optional;
import java.util.List;

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
import com.dentalclinic.model.Patient;
import com.dentalclinic.model.User;
import com.dentalclinic.repository.DoctorRepository;
import com.dentalclinic.repository.UserRepository;
import com.dentalclinic.service.DoctorService;
import com.dentalclinic.service.MedicalRecordService;
import com.dentalclinic.service.PatientService;

@Controller
//@RequestMapping("/medical-records")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/doctor/medical-records")
    public String listMedicalRecords(Model model) {
        // Récupérer le nom d'utilisateur de l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Récupérer l'ID de l'utilisateur à partir du nom d'utilisateur
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        Long userId = userOptional.get().getId();

        // Récupérer l'ID du docteur à partir de l'ID de l'utilisateur
        Optional<Doctor> doctorOptional = doctorRepository.findByUserId(userId);
        if (doctorOptional.isEmpty()) {
            throw new RuntimeException("Docteur non trouvé");
        }
        Long doctorId = doctorOptional.get().getId();
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByDoctorId(doctorId);
        model.addAttribute("medicalRecords", medicalRecords);

        return "doctor/medical-records";
    }

    @GetMapping("/doctor/medical-records/new")
    public String showMedicalRecordForm(@RequestParam("patientId") Long patientId,
                                        @RequestParam("doctorId") Long doctorId, Model model) {
        Patient patient = patientService.getPatientById(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient non trouvé pour l'ID : " + patientId);
        }
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            throw new IllegalArgumentException("Docteur non trouvé pour l'ID : " + doctorId);
        }
        
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setPatient(patient);
        medicalRecord.setDoctor(doctor);
        model.addAttribute("medicalRecord", medicalRecord);
        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);
        return "doctor/medical-record-form";
    }

    @PostMapping("/medical-records/save")
    public String saveMedicalRecord(@ModelAttribute("medicalRecord") MedicalRecord medicalRecord,
                                    @RequestParam("patientId") Long patientId,
                                    @RequestParam("doctorId") Long doctorId) {
        medicalRecordService.saveMedicalRecord(medicalRecord, patientId, doctorId);
        return "redirect:/doctor/patients/details/" + patientId;
    }

    @GetMapping("/doctor/medical-records/details/{id}")
    public String viewPatientMedicalRecordDetails(@PathVariable Long id, Model model) {
        MedicalRecord medicalRecord =medicalRecordService.getMedicalRecordById(id);
        model.addAttribute("medicalRecord", medicalRecord);
        return "doctor/medical-record-details";
    }

    @GetMapping("/doctor/medical-records/edit/{id}")
    public String editMedicalRecordForm(@PathVariable Long id, Model model) {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(id);
        Patient patient =medicalRecord.getPatient();
        model.addAttribute("medicalRecord", medicalRecord);
        model.addAttribute("patient", patient);
        return "doctor/medical-record-form";
    }

    @GetMapping("/doctor/medical-records/delete/{id}")
    public String deleteMedicalRecord(@PathVariable Long id) {
        MedicalRecord medicalRecord=medicalRecordService.getMedicalRecordById(id);
        Long patientId=medicalRecord.getPatient().getId();
        medicalRecordService.deleteMedicalRecord(id);
        return "redirect:/doctor/patients/details/" + patientId;
    }
}
