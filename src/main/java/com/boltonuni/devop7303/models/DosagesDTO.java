package com.boltonuni.devop7303.models;

import com.boltonuni.devop7303.entity.DosageIntake;
import com.boltonuni.devop7303.entity.Schedules;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


public class DosagesDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Schedules schedule;
    private String description;
    @NotNull(message = "Time of intake required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime intakeTime;
    private String dosage;
    private boolean taken;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateCreated;
    private LocalDateTime remindAt;
    private String reminded;
    private List<DosageIntake> datesTaken;

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

    public List<DosageIntake> getDatesTaken() {
        return datesTaken;
    }

    public void setDatesTaken(List<DosageIntake> datesTaken) {
        this.datesTaken = datesTaken;
    }

    @Override
    public String toString() {
        return "DosagesDTO{" +
                "id=" + id +
//                ", schedule=" + schedule +
                ", description='" + description + '\'' +
                ", intakeTime=" + intakeTime +
                ", dosage='" + dosage + '\'' +
                ", datesTaken="+datesTaken+'\''+
                ", taken=" + taken +
                ", dateCreated=" + dateCreated +
                ", remindAt=" + remindAt +
                ", reminded=" + reminded +
                '}';
    }
}
