package com.boltonuni.devop7303.controllers;

import com.boltonuni.devop7303.models.Response;
import com.boltonuni.devop7303.service.DoctorHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescriptions")
public class DoctorHistoryController {

    @Autowired
    private DoctorHistoryService doctorHistoryService;


    @GetMapping("/doctor/{doctorId}")
    public Response getPrescriptionsByDoctor(@PathVariable Long doctorId) {
        return doctorHistoryService.getPrescriptionsByDoctor(doctorId);
    }
}
