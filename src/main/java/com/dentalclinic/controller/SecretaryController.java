package com.dentalclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dentalclinic.service.AppointmentService;
import com.dentalclinic.service.InvoiceService;
import com.dentalclinic.service.PatientService;

@Controller
@RequestMapping("/secretary") // Toutes les routes de ce contrôleur commencent par /secretary
public class SecretaryController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/dashboard") // Route pour le tableau de bord de la secrétaire
    public String dashboard(Model model) {
        // Récupérer les données pour le tableau de bord
        long totalPatients = patientService.getAllPatients().size();
        long appointmentsToday = appointmentService.getAppointmentsToday().size();
        long unpaidInvoices = invoiceService.getUnpaidInvoices().size();

        // Ajouter les données au modèle
        model.addAttribute("totalPatients", totalPatients);
        model.addAttribute("appointmentsToday", appointmentsToday);
        model.addAttribute("unpaidInvoices", unpaidInvoices);

        return "secretary/dashboard"; // Nom du template Thymeleaf (secretary/dashboard.html)
    }
}