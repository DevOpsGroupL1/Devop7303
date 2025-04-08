package com.boltonuni.devop7303.controllers;

import com.boltonuni.devop7303.entity.DoctorHistory;
import com.boltonuni.devop7303.entity.Schedules;
import com.boltonuni.devop7303.service.DoctorPrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/")
public class DoctorPrescriptionController {

    private final DoctorPrescriptionService doctorPrescriptionService;

    @Autowired
    public DoctorPrescriptionController(DoctorPrescriptionService doctorPrescriptionService) {
        this.doctorPrescriptionService = doctorPrescriptionService;
    }

    @GetMapping("/doctor-prescriptions/{doctorId}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<List<Schedules>> getDoctorPrescriptionHistory(@PathVariable String doctorId) {
        List<Schedules> prescriptions = doctorPrescriptionService.getDoctorPrescriptionHistory(doctorId);

        if (prescriptions.isEmpty()) {
            return ResponseEntity.ok().body(prescriptions); // Returns an empty array if no prescriptions
        }
        return ResponseEntity.ok().body(prescriptions);
    }
}
