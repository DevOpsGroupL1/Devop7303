package com.boltonuni.devop7303.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DosageIntake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    private int dosageId;
    private String userId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateCreated;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dosage_id", nullable = false)
    private Dosages dosage;

    public DosageIntake() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getDosageId() {
//        return dosageId;
//    }
//
//    public void setDosageId(int dosageId) {
//        this.dosageId = dosageId;
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Dosages getDosage() {
        return dosage;
    }

    public void setDosage(Dosages dosage) {
        this.dosage = dosage;
    }

    @Override
    public String toString() {
        return "DosageIntake{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", dateCreated=" + dateCreated +
//                ", dosage=" + dosage +
                '}';
    }
}
