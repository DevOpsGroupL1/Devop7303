package com.boltonuni.devop7303.service;

import com.boltonuni.devop7303.entity.DoctorScheduleDetail;
import com.boltonuni.devop7303.models.Response;
import com.boltonuni.devop7303.repository.SchedulesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorScheduleService {
    @Autowired
    SchedulesRepo repo;
    public Response getScheduleById(Long id) {

        return new Response("Success", "00", 1);
    }
}
