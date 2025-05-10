package com.boltonuni.devop7303.service;

import com.boltonuni.devop7303.entity.DosageIntake;
import com.boltonuni.devop7303.repository.DosageIntakeRepo;
import com.boltonuni.devop7303.repository.DosagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DosageIntakeService {
    @Autowired
    DosageIntakeRepo dRepo;
    @Autowired
    DosagesRepo dosagesRepo;

    public DosageIntake getLastDosageTaken(String userId){
        DosageIntake taken = dRepo.findDosageIntakeByUserId(userId);
        return taken;
    }

    public void getUpcomingDosages(String userId){
        DosageIntake dosageIntake = getLastDosageTaken(userId);
        dosagesRepo.findById(dosageIntake.getDosage().getId());
    }
}
