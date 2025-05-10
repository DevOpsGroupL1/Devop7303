package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.DosageIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DosageIntakeRepo extends JpaRepository<DosageIntake, Integer> {
    DosageIntake findDosageIntakeByUserId(String userId);
    DosageIntake findTop1ByUserIdOrderByDateCreatedDesc(String userId);
}
