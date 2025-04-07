package com.boltonuni.devop7303.controllers;

import com.boltonuni.devop7303.entity.DoctorHistory;
import com.boltonuni.devop7303.service.DoctorPrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor-prescriptions")
public class DoctorPrescriptionController {

    private final DoctorPrescriptionService doctorPrescriptionService;

    @Autowired
    public DoctorPrescriptionController(DoctorPrescriptionService doctorPrescriptionService) {
        this.doctorPrescriptionService = doctorPrescriptionService;
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<List<DoctorHistory>> getDoctorPrescriptionHistory(@PathVariable Long doctorId) {
        List<DoctorHistory> prescriptions = doctorPrescriptionService.getDoctorPrescriptionHistory(doctorId);

        if (prescriptions.isEmpty()) {
            return ResponseEntity.ok().body(prescriptions); // Returns an empty array if no prescriptions
        }
        return ResponseEntity.ok().body(prescriptions);
    }
}
