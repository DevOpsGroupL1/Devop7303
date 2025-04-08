package com.boltonuni.devop7303.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "doctor_schedule_detail")
public class DoctorScheduleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "doc_id", nullable = false)
    private Long docId;

    @Column(name = "drug_name", nullable = false)
    private String drugName;

    @Column(name = "description")
    private String description;

    @Column(name = "prescription", nullable = false)
    private String prescription;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "ailment", nullable = false)
    private String ailment;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getDocId() { return docId; }
    public void setDocId(Long docId) { this.docId = docId; }

    public String getDrugName() { return drugName; }
    public void setDrugName(String drugName) { this.drugName = drugName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getAilment() { return ailment; }
    public void setAilment(String ailment) { this.ailment = ailment; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }
}
