package com.boltonuni.devop7303.service;

import com.boltonuni.devop7303.entity.DoctorHistory;
import com.boltonuni.devop7303.models.Response;
import com.boltonuni.devop7303.service.DoctorHistoryService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DoctorHistoryService {

    private final com.boltonuni.devop7303.repository.DoctorHistoryRepository doctorHistoryRepository;

    public DoctorHistoryService(com.boltonuni.devop7303.repository.DoctorHistoryRepository doctorHistoryRepository) {
        this.doctorHistoryRepository = doctorHistoryRepository;
    }

    public Response getPrescriptionsByDoctor(String doctorId) {
            List<DoctorHistory> list = doctorHistoryRepository.find;
        return new Response("Success", "00", list);
    }
}