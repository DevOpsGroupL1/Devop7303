package com.boltonuni.devop7303.service;

import com.boltonuni.devop7303.entity.DosageIntake;
import com.boltonuni.devop7303.entity.Dosages;
import com.boltonuni.devop7303.entity.Schedules;
import com.boltonuni.devop7303.entity.User;
import com.boltonuni.devop7303.models.Response;
import com.boltonuni.devop7303.repository.DosageIntakeRepo;
import com.boltonuni.devop7303.repository.DosagesRepo;
import com.boltonuni.devop7303.repository.SchedulesRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Autowired
    private EmailService emailService;
    @Autowired
    DosagesRepo dosagesRepo;
    @Autowired
    DosageIntakeRepo dosageIntakeRepo;

    /**
     * Save schedule
     * @param schedules Schedule request payload to persist
     * @return save Schedule data
     */
    public Response saveSchedule(Schedules schedules){
        try{
            User user = userService.findById(schedules.getUserId());
            System.out.println("Doctor user"+user.toString());
            if(user==null)
                return new Response("Failed", "00", "Account not found");
            schedules.setUser(user);
            User doctor = userService.findById(schedules.getDoctorId());
            System.out.println("Doctor doctor"+doctor.toString());
            schedules.setDoctor(doctor);

            UUID id = UUID.randomUUID();
            schedules.setId(id.toString());
            List<Dosages> dosagesList = schedules.getDosages().stream().map((dosage)->{
                Dosages dose = dosage;
                LocalDateTime timeToTake = dose.getIntakeTime();
                LocalDateTime reminder = timeToTake.minusMinutes(10);
                dose.setRemindAt(reminder);
                dose.setReminded("No");
                dose.setSchedule(schedules);
                return dose;
            }).collect(Collectors.toList());
            schedules.setDosages(dosagesList);
            schedules.setDateCreated(LocalDateTime.now());
            System.out.println("User final: "+schedules);
            Schedules saveSchedule = schedulesRepo.save(schedules);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String message = "<p>This is to notify you that you have a new medical prescription.</p>";
                    String detail = "<p><b>Doctor's name: </b>"+doctor.getfName()+" </p> <p><b>Prescription Description: </b>"+schedules.getDescription()+" </p> <p><b>Prescription: </b>"+schedules.getPrescription()+"</p>";
                    message = message+detail;
                    emailService.schedulingNotification(user.getfName(), user.getEmail(), "New Prescription", message);
                }
            });
            thread.start();
            return new Response("Success", "00", saveSchedule);
        }
        catch (Throwable th){
            th.printStackTrace();
            LOGGER.debug("saveSchedule: ",th);
            return new Response("Error", "99", "Operation failed");
        }

    }

    /**
     * Get logged in patient's schedule
     * @param userId
     * @return the patient's last 10 schedules
     */
    //Get last 10 patient's prescription
    public Response getLast10Schedule(String userId) {;
        try {
            List<Schedules> schedules = schedulesRepo.findLast10SchedulesByUserId(userId);
            return new Response("success","00", schedules);
        }catch (Throwable th){
            LOGGER.debug("getLast10Schedule: ", th);
            return new Response("Failed","99", "Operation failed");
        }
    }

    /**
     * Get logged in doctors schedule
     * @param userId
     * @return the doctor's last 50 patient schedules
     */
    public Response getLast50Schedule(String userId) {
        try {
            List<Schedules> schedules = schedulesRepo.findLast50SchedulesByDoctorId(userId);
            return new Response("success","00", schedules);
        }catch (Throwable th){
            LOGGER.debug("getLast10Schedule: ", th);
            return new Response("Failed","99", "Operation failed");
        }
    }

    public Response updateDosageIntake(int dosageId, String schedId){
        try {
            Schedules schedules = schedulesRepo.findSchedulesById(schedId, dosageId);
            LOGGER.info("Schedules: ");
            LOGGER.info(schedules.toString());


            System.out.println(schedules.getDosages().size());
            Dosages dosages = schedules.getDosages().stream().filter((dose)->dose.getId()==dosageId).collect(Collectors.toList()).get(0);
            LOGGER.info("DOsages: ");
            LOGGER.info(dosages.toString());


            if(dosages==null)
                return new Response("Failed", "80", "Dosage not found");

            LOGGER.info("Before dosages.................");
            dosages.setTaken(true);
            Dosages dosage = dosagesRepo.save(dosages);
            LOGGER.info("Before dosages 1:.................{}", dosage);
            DosageIntake userIntake = dosageIntakeRepo.findDosageIntakeByUserId(schedules.getUser().getId());
            LOGGER.info("Before dosages 2:.................{}", userIntake);
//            LOGGER.info("Before dosages 3:.................{}", userIntake.toString());
            DosageIntake intake = null;
            if(userIntake==null)
                intake = new DosageIntake();
            intake.setDosageId(dosage.getId());
            intake.setUserId(schedules.getUser().getId());
            intake.setDateCreated(LocalDateTime.now());
            dosageIntakeRepo.save(intake);

            return new Response("Success", "00", "Dosage have been updated.");

        }catch (Throwable th){
            th.printStackTrace();
            LOGGER.debug("updateDosageIntake: ",th);
            return new Response("Failed", "99", "Operation failed");
        }
    }

    public Dosages getLastTakenDosage(String userId){
        DosageIntake lastDose = dosageIntakeRepo.findDosageIntakeByUserId(userId);
        if(lastDose==null)
            return null;
        Dosages lastDosage = dosagesRepo.findById(lastDose.getDosageId()).orElse(null);
        return lastDosage;
    }

    public Response loadPatientHistory(String patientEmail){
        User user = userService.findByEmail(patientEmail);
        System.out.println(user.getId());
        List<Schedules> history = schedulesRepo.loadPatientHistory(user.getId());
        Response response = new Response("Success", "00", history);
        return response;
    }

    public List<Schedules> loadUpcoming(User user){
        List<Schedules> schedules = schedulesRepo.loadUpcomingDosage(user.getId(), LocalDate.now());
        LOGGER.info("Upcoming schedules.........", schedules);
        if(schedules!=null && schedules.size()>2)
            return schedules.stream().limit(2).collect(Collectors.toList());
        return schedules;
    }

    public Response getPatientPrescription(String doctorEmail, String userId){
        try {
            User user = userService.findByEmail(doctorEmail);
            if(user==null)
                return new Response("Account does not exist", "99",null);
            System.out.println("Doc id: "+user.getId());
            List<Schedules> patientPrescription = schedulesRepo.getPatientPrescription(user.getId(), userId);
            return new Response("Success", "00", patientPrescription);
        }catch (Throwable th){
            LOGGER.debug("getPatientPrescription>>",th);
            return new Response("Error Occurred", "99", null);
        }
    }

    public Response upcoming(String email){
        User user = userService.findByEmail(email);
        List<Schedules> upcoming = loadUpcoming(user);
        return new Response("Success", "00", upcoming==null?new ArrayList<>():upcoming);
    }
}
