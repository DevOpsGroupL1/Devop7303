package com.boltonuni.devop7303.service;

import com.boltonuni.devop7303.entity.Dosages;
import com.boltonuni.devop7303.entity.Schedules;
import com.boltonuni.devop7303.repository.DosagesRepo;
import com.boltonuni.devop7303.repository.SchedulesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class Cron {
    @Autowired
    private DosagesRepo dosagesRepo;
    @Autowired
    private SchedulesRepo schedulesRepo;
    @Autowired
    private EmailService emailService;

    @Async
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    @Transactional
    public void sendReminderForUpcoming(){
        List<Dosages> dosagesList = dosagesRepo.findDosagesMoreThan2Minutes(LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        for(int i=0; i<dosagesList.size(); i++){
            Dosages dosages = dosagesList.get(i);
            Schedules schedule = schedulesRepo.findSchedulesById(dosagesList.get(i).getSchedule().getId(), dosagesList.get(i).getId());
            String message = "<p>This is to remind you that you have <b>"+schedule.getDrugName()+"</b> prescription to take at "+dosagesList.get(i).getIntakeTime()+"</p>";
            emailService.schedulingNotification(schedule.getUser().getfName(), schedule.getUser().getEmail(),"REMINDER: Upcoming Dosage Intake", message);
            dosages.setReminded("Yes");
            dosagesRepo.save(dosages);
        }
    }

    @Async
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    @Transactional
    public void sendReminderForDue(){
        List<Dosages> dosagesList = dosagesRepo.findDosagesNotMoreThan1Minutes(LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        for(int i=0; i<dosagesList.size(); i++){
            Dosages dosages = dosagesList.get(i);
            Schedules schedule = schedulesRepo.findSchedulesById(dosages.getSchedule().getId(), dosages.getId());
            String message = "<p>This is a final reminder for <b>"+schedule.getDrugName()+"</b> prescription due at "+dosages.getIntakeTime()+"</p>";
            emailService.schedulingNotification(schedule.getUser().getfName(), schedule.getUser().getEmail(),"REMINDER: Due Dosage Intake", message);
        }
    }
}
