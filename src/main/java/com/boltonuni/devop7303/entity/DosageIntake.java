package com.boltonuni.devop7303.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class DosageIntake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int dosageId;
    private String userId;
    private LocalDateTime dateCreated;

    public DosageIntake() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDosageId() {
        return dosageId;
    }

    public void setDosageId(int dosageId) {
        this.dosageId = dosageId;
    }

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

    @Override
    public String toString() {
        return "DosageIntake{" +
                "id=" + id +
                ", dosageId=" + dosageId +
                ", userId='" + userId + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
