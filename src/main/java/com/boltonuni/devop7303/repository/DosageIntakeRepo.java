package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.DosageIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DosageIntakeRepo extends JpaRepository<DosageIntake, Integer> {
    DosageIntake findDosageIntakeByUserId(String userId);
}
