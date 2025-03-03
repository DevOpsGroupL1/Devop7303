package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulesRepo extends JpaRepository<Schedules, Integer> {
    @Query("SELECT s, d FROM Schedules s, DoctorDetail d WHERE s.user.id = :userId AND d.user.id = s.doctor.id ORDER BY s.dateCreated DESC")
    List<Schedules> findLast10SchedulesByUserId(String userId);
}
