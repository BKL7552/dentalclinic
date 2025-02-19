package com.dentalclinic.controller;

import com.dentalclinic.model.Doctor;
import com.dentalclinic.model.User;
import com.dentalclinic.repository.DoctorRepository;
import com.dentalclinic.repository.UserRepository;
import com.dentalclinic.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private AppointmentService appointmentService;

    //@Autowired
    //private MedicalRecordService medicalRecordService;

    //@Autowired
    //private TreatmentSheetService treatmentSheetService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/dashboard")
    public String doctorDashboard(Model model) {
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

        // Récupérer les données pour le tableau de bord
        int appointmentsToday = appointmentService.getAppointmentsTodayByDoctorId(doctorId);
        //int pendingMedicalRecords = medicalRecordService.getPendingMedicalRecordsByDoctorId(doctorId);
        //int ongoingTreatmentSheets = treatmentSheetService.getOngoingTreatmentSheetsByDoctorId(doctorId);

        // Ajouter les données au modèle
        model.addAttribute("appointmentsToday", appointmentsToday);
        //model.addAttribute("pendingMedicalRecords", pendingMedicalRecords);
        //model.addAttribute("ongoingTreatmentSheets", ongoingTreatmentSheets);

        return "doctor/dashboard"; // Vue du tableau de bord médecin
    }
}