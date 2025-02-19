package com.dentalclinic.repository;

import com.dentalclinic.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Trouver tous les paiements associés à une facture
    List<Payment> findByInvoiceId(Long invoiceId);
}