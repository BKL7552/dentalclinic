package com.dentalclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dentalclinic.model.Invoice;
import com.dentalclinic.service.InvoiceService;
import com.dentalclinic.service.PatientService;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PatientService patientService;

    @GetMapping
    public String listInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.getAllInvoices());
        return "invoices/list";
    }

    @GetMapping("/new")
    public String showInvoiceForm(Model model) {
        model.addAttribute("invoice", new Invoice());
        model.addAttribute("patients", patientService.getAllPatients());
        return "invoices/form";
    }

    @PostMapping("/save")
    public String saveInvoice(@ModelAttribute("invoice") Invoice invoice,
                              @RequestParam("patientId") Long patientId) {
        invoiceService.saveInvoice(invoice, patientId);
        return "redirect:/invoices";
    }

    @GetMapping("/edit/{id}")
    public String editInvoiceForm(@PathVariable Long id, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        model.addAttribute("invoice", invoice);
        model.addAttribute("patients", patientService.getAllPatients());
        return "invoices/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return "redirect:/invoices";
    }


}
