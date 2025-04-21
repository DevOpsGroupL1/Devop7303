package com.boltonuni.devop7303.service;

import com.boltonuni.devop7303.entity.DoctorScheduleDetail;
import com.boltonuni.devop7303.models.Response;
import org.springframework.stereotype.Service;

@Service
public class DoctorScheduleService {
    public Response getScheduleById(Long id) {
        return new Response("Success", "00", 1);
    }
}
