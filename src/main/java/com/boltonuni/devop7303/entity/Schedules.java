package com.boltonuni.devop7303.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Schedules {
    @Id
    private String id;

//    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @Transient
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", nullable = false, referencedColumnName = "id")
    private User doctor;

    @Transient
    private String doctorId;

    @NotBlank(message = "Drug name required")
    private String drugName;

    @NotBlank(message = "Drug description required")
    private String description;

    @NotBlank(message = "Drug Prescription required")
    private String prescription;

    @NotNull(message = "Start date required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    @NotBlank(message = "Ailment name required")
    private String ailment;

    private boolean completed;
    private LocalDateTime dateCreated;

//    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private List<Dosages> dosages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getAilment() {
        return ailment;
    }

    public void setAilment(String ailment) {
        this.ailment = ailment;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Dosages> getDosages() {
        return dosages;
    }

    public void setDosages(List<Dosages> dosages) {
        this.dosages = dosages;
    }

    @Override
    public String toString() {
        return "Schedules{" +
                "id=" + id +
                ", user=" + user +
                ", userId='" + userId + '\'' +
                ", doctor='" + doctor + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", drugName='" + drugName + '\'' +
                ", description='" + description + '\'' +
                ", prescription='" + prescription + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", ailment='" + ailment + '\'' +
                ", completed=" + completed +
                ", dateCreated=" + dateCreated +
                ", dosages=" + dosages +
                '}';
    }
}
