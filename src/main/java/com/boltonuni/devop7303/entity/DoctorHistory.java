package com.boltonuni.devop7303.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prescriptions")
public class DoctorHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long doctorId;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private String medicationName;

    @Column(nullable = false)
    private LocalDate datePrescribed;

    public DoctorHistory() {}

    public DoctorHistory(Long doctorId, Long patientId, String medicationName, LocalDate datePrescribed) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.medicationName = medicationName;
        this.datePrescribed = datePrescribed;
    }

    public Long getId() {
        return id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public LocalDate getDatePrescribed() {
        return datePrescribed;
    }
}
