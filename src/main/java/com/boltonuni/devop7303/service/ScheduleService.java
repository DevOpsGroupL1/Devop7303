package com.boltonuni.devop7303.service;

import com.boltonuni.devop7303.entity.Dosages;
import com.boltonuni.devop7303.entity.Schedules;
import com.boltonuni.devop7303.entity.User;
import com.boltonuni.devop7303.models.Response;
import com.boltonuni.devop7303.repository.SchedulesRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired
    SchedulesRepo schedulesRepo;
    @Autowired
    private UserService userService;

    public Response saveSchedule(Schedules schedules){
        try{
            User user = userService.findById(schedules.getUserId());
            if(user==null)
                return new Response("Failed", "00", "Account not found");
            schedules.setUser(user);

            UUID id = UUID.randomUUID();
            schedules.setId(id.toString());
            List<Dosages> dosagesList = schedules.getDosages().stream().map((dosage)->{
                Dosages dose = dosage;
                dose.setSchedule(schedules);
                return dose;
            }).collect(Collectors.toList());
            schedules.setDosages(dosagesList);
            schedules.setDateCreated(LocalDateTime.now());
            System.out.println("User final: "+schedules);
            Schedules saveSchedule = schedulesRepo.save(schedules);
            return new Response("Success", "00", saveSchedule);
        }
//        catch (DataIntegrityViolationException di){
//
//        }
        catch (Throwable th){
            th.printStackTrace();
            LOGGER.debug("saveSchedule: ",th);
            return new Response("Error", "99", "Operation failed");
        }

    }
}
