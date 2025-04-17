package com.boltonuni.devop7303.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Dosages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @JsonBackReference
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false, referencedColumnName = "id")
    private Schedules schedule;
    private String description;
    @NotNull(message = "Time of intake required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime intakeTime;
    private String dosage;
    private boolean taken;
    private LocalDateTime dateCreated;
    private LocalDateTime remindAt;
    private String reminded;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Schedules getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedules schedule) {
        this.schedule = schedule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getIntakeTime() {
        return intakeTime;
    }

    public void setIntakeTime(LocalDateTime intakeTime) {
        this.intakeTime = intakeTime;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getRemindAt() {
        return remindAt;
    }

    public void setRemindAt(LocalDateTime remindAt) {
        this.remindAt = remindAt;
    }

    public String getReminded() {
        return reminded;
    }

    public void setReminded(String reminded) {
        this.reminded = reminded;
    }

    @Override
    public String toString() {
        return "Dosages{" +
                "id=" + id +
//                ", schedule=" + schedule +
                ", description='" + description + '\'' +
                ", intakeTime=" + intakeTime +
                ", dosage='" + dosage + '\'' +
                ", taken=" + taken +
                ", dateCreated=" + dateCreated +
                ", remindAt=" + remindAt +
                ", reminded=" + reminded +
                '}';
    }
}
