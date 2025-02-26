package com.boltonuni.devop7303.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fName;
    private String mName;
    private String lName;
    private String email;
    private String password;
    private boolean isActive;
    private LocalDateTime dateCreated;
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserDetail userDetail;
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private DoctorDetail doctorDetail;
    @Transient
    private Role userRole;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public DoctorDetail getDoctorDetail() {
        return doctorDetail;
    }

    public void setDoctorDetail(DoctorDetail doctorDetail) {
        this.doctorDetail = doctorDetail;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", mName='" + mName + '\'' +
                ", lName='" + lName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", dateCreated=" + dateCreated +
                ", userDetail=" + userDetail +
                ", doctorDetail=" + doctorDetail +
                ", userRole=" + userRole +
                '}';
    }
}
