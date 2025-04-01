package com.boltonuni.devop7303.controller;

import com.boltonuni.devop7303.entity.DoctorScheduleDetail;
import com.boltonuni.devop7303.service.DoctorScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
public class DoctorScheduleController {

    @Autowired
    private DoctorScheduleService doctorScheduleService;

    @GetMapping("/{id}")
    public DoctorScheduleDetail getScheduleById(@PathVariable Long id) {
        return doctorScheduleService.getScheduleById(id);
    }
}
