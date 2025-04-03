package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.Schedules;
import com.boltonuni.devop7303.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SchedulesRepo extends JpaRepository<Schedules, Integer> {
    @Query("SELECT s, d FROM Schedules s, DoctorDetail d WHERE s.user.id = :userId AND d.user.id = s.doctor.id ORDER BY s.dateCreated DESC")
    List<Schedules> findLast10SchedulesByUserId(String userId);

    @Query("SELECT s, d, u FROM Schedules s, DoctorDetail d, UserDetail u WHERE s.doctor.id = :userId AND u.user.id = s.user.id AND s.doctor.id = d.user.id  ORDER BY s.dateCreated DESC")
    List<Schedules> findLast50SchedulesByDoctorId(String userId);

    @Query("SELECT s, d FROM Schedules s, Dosages d WHERE s.id = :schedId AND d.id = :dosageId")
    Schedules findSchedulesById(String schedId, int dosageId);

    @Query(value = "SELECT * from Schedules s where user_id = ?",nativeQuery = true)
    List<Schedules> loadPatientHistory(@Param("userId") String userId);

    List<Schedules> findSchedulesByUserAndStartDateGreaterThanEqual(User user, LocalDateTime startDate);

    @Query(value = "SELECT s.* FROM Schedules s JOIN Dosages d ON d.schedule_id= s.id AND s.user_id =? AND d.taken = 0 AND DATE(d.intake_time) =?", nativeQuery = true)
    List<Schedules> loadUpcomingDosage(@Param("userId") String userId, LocalDate date);
}
