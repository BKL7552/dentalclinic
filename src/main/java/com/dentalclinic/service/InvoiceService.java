package com.dentalclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dentalclinic.model.Invoice;
import com.dentalclinic.model.Patient;
import com.dentalclinic.model.Payment;
import com.dentalclinic.repository.InvoiceRepository;
import com.dentalclinic.repository.PatientRepository;
import com.dentalclinic.repository.PaymentRepository;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    public Invoice saveInvoice(Invoice invoice, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        invoice.setPatient(patient);
        return invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    public List<Invoice> getInvoicesByPatientId(Long patientId) {
        return invoiceRepository.findByPatientId(patientId);
    }

    public List<Invoice> getUnpaidInvoices() {
        return invoiceRepository.findByIsPaidFalse();
    }
    public List<Payment> getPaymentsByInvoiceId(Long invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId);
    }

    public Optional<Invoice> getInvoiceByTreatmentSheetId(Long treatmentSheetId) {
        return invoiceRepository.findByTreatmentSheetId(treatmentSheetId);
    }
}
