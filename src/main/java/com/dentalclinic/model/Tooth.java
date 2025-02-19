package com.dentalclinic.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Tooth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Nom de la dent (ex: "Molaire supérieure droite")
    private String condition; // État de la dent (ex: "Cariée", "Saine")
    private String notes; // Notes supplémentaires

    @OneToMany(mappedBy = "tooth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Treatment> treatments; // Les traitements associés à cette dent

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tooth tooth = (Tooth) o;
        return Objects.equals(id, tooth.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString
    @Override
    public String toString() {
        return "Tooth{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", condition='" + condition + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}