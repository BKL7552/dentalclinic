package com.dentalclinic.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class TreatmentSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String generalDescription; // Description générale de la fiche de traitement

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient; // Le patient associé

    @OneToMany(mappedBy = "treatmentSheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Treatment> treatments; // Les traitements associés à cette fiche

    @OneToOne(mappedBy = "treatmentSheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Invoice invoice; // La facture associée

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreatmentSheet that = (TreatmentSheet) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString
    @Override
    public String toString() {
        return "TreatmentSheet{" +
                "id=" + id +
                ", generalDescription='" + generalDescription + '\'' +
                '}';
    }
}