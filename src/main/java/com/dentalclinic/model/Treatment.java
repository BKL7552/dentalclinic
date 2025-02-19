package com.dentalclinic.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description; // Description du traitement pour cette dent

    @ManyToOne
    @JoinColumn(name = "tooth_id")
    private Tooth tooth; // La dent concernée

    @ManyToOne
    @JoinColumn(name = "treatment_sheet_id")
    private TreatmentSheet treatmentSheet; // La fiche de traitement associée

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tooth getTooth() {
        return tooth;
    }

    public void setTooth(Tooth tooth) {
        this.tooth = tooth;
    }

    public TreatmentSheet getTreatmentSheet() {
        return treatmentSheet;
    }

    public void setTreatmentSheet(TreatmentSheet treatmentSheet) {
        this.treatmentSheet = treatmentSheet;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Treatment treatment = (Treatment) o;
        return Objects.equals(id, treatment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString
    @Override
    public String toString() {
        return "Treatment{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}