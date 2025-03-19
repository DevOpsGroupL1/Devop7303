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

    @Query("SELECT s, d, u FROM Schedules s, DoctorDetail d, UserDetail u WHERE s.doctor.id = :userId AND u.user.id = s.user.id AND s.doctor.id = d.user.id  ORDER BY s.dateCreated DESC")
    List<Schedules> findLast50SchedulesByDoctorId(String userId);

    @Query("SELECT s, d FROM Schedules s, Dosages d WHERE s.id = :schedId AND d.id = :dosageId")
    Schedules findSchedulesById(String schedId, int dosageId);
}
