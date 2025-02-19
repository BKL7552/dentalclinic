package com.dentalclinic.controller;

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

import com.dentalclinic.model.Doctor;
import com.dentalclinic.model.Patient;
import com.dentalclinic.model.User;
import com.dentalclinic.repository.DoctorRepository;
import com.dentalclinic.repository.UserRepository;
import com.dentalclinic.service.DoctorService;
import com.dentalclinic.service.PatientService;


@Controller
//@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/secretary/patients")
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "secretary/patients";
    }

    @GetMapping("/patients/new")
    public String showPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "secretary/patient-form";
    }

    @PostMapping("/patients/save")
    public String savePatient(@ModelAttribute("patient") Patient patient) {
        patientService.savePatient(patient);
        return "redirect:/secretary/patients";
    }

    @GetMapping("/secretary/patients/details/{id}")
    public String viewPatientDetails(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "secretary/patient-details";
    }

    @GetMapping("/doctor/patients/details/{id}")
    public String viewPatientDetailsForDoctor(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id);

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
        Doctor doctor =doctorService.getDoctorById(doctorId);
        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);
        return "doctor/patient-details";
    }

    @GetMapping("/patients/edit/{id}")
    public String editPatientForm(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.getPatientById(id));
        return "secretary/patient-form";
    }

    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/secretary/patients";
    }
}