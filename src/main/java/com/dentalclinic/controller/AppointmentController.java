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

import org.springframework.web.bind.annotation.RequestParam;

import com.dentalclinic.model.Appointment;
import com.dentalclinic.model.Doctor;
import com.dentalclinic.model.Patient;
import com.dentalclinic.model.User;
import com.dentalclinic.repository.DoctorRepository;
import com.dentalclinic.repository.UserRepository;
import com.dentalclinic.service.AppointmentService;
import com.dentalclinic.service.DoctorService;
import com.dentalclinic.service.PatientService;

@Controller
//@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserRepository userRepository;

    

    @GetMapping("/secretary/appointments")
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "secretary/appointments";
    }

    @GetMapping("/doctor/appointments")
    public String getAppointmentsByDoctor(Model model) {
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

        // Récupérer les rendez-vous du docteur
        model.addAttribute("appointments", appointmentService.getAppointmentsByDoctorId(doctorId));

        return "doctor/appointments"; // Vue de la liste des rendez-vous du docteur
    }


    @GetMapping("/appointments/new")
    public String showAddAppointmentForm(@RequestParam("patientId") Long patientId, Model model) {
        // Récupère le patient par son ID
        Patient patient = patientService.getPatientById(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient non trouvé pour l'ID : " + patientId);
        }

        // Initialise un nouvel objet Appointment avec le patient
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);

        // Ajoute l'objet au modèle
        model.addAttribute("appointment", appointment);
        model.addAttribute("patient", patient);
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "secretary/appointment-form";
    }


    @PostMapping("/appointments/save")
    public String saveAppointment(@ModelAttribute("appointment") Appointment appointment,
                                @RequestParam("patientId") Long patientId,
                                @RequestParam("doctorId") Long doctorId) {
        //System.out.println("l'identifient du patient est: "+patientId);
        appointmentService.saveAppointment(appointment, patientId, doctorId);
        return "redirect:/secretary/patients/details/" + patientId;
    }

    @GetMapping("/appointments/edit/{id}")
    public String editAppointmentForm(@PathVariable Long id, Model model) {
        
        Appointment appointment = appointmentService.getAppointmentById(id);
        Patient patient =appointment.getPatient();
        model.addAttribute("appointment", appointment);
        model.addAttribute("patient", patient);
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "secretary/appointment-form";
    }

    @GetMapping("/appointments/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        Long patientId = appointment.getPatient().getId();
        appointmentService.deleteAppointment(id);
        return "redirect:/secretary/patients/details/" + patientId;
    }


}
