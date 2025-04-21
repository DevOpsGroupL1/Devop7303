package com.boltonuni.devop7303.service;

import com.boltonuni.devop7303.entity.DoctorHistory;
import com.boltonuni.devop7303.entity.Schedules;
import com.boltonuni.devop7303.repository.DoctorPrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DoctorPrescriptionService {

    private final DoctorPrescriptionRepository doctorPrescriptionRepository;

    @Autowired
    public DoctorPrescriptionService(DoctorPrescriptionRepository doctorPrescriptionRepository) {
        this.doctorPrescriptionRepository = doctorPrescriptionRepository;
    }

    public List<Schedules> getDoctorPrescriptionHistory(String doctorId) {
        return doctorPrescriptionRepository.loadDoctorPrescriptionHistory(doctorId);
    }
}
