package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.Dosages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DosagesRepo extends JpaRepository<Dosages, Integer> {
    @Query(value = "SELECT * FROM dosages where DATE(intake_time) = CURRENT_DATE AND taken=0 AND remind_at <=? and ? BETWEEN remind_at AND intake_time " +
            "AND TIMESTAMPDIFF(MINUTE, ?, intake_time) > 2 AND IFNULL(reminded, 'No') <> 'Yes'", nativeQuery = true)
    List<Dosages> findDosagesMoreThan2Minutes(@Param("currentDate1")LocalDateTime currentDate1, @Param("currentDate2")LocalDateTime currentDate2, @Param("currentDate3")LocalDateTime currentDate3);

    @Query(value = "SELECT * FROM dosages where DATE(intake_time) = CURRENT_DATE AND taken=0 AND remind_at <=? and ? BETWEEN remind_at AND intake_time " +
            "AND TIMESTAMPDIFF(MINUTE, ?, intake_time) <= 1", nativeQuery = true)
    List<Dosages> findDosagesNotMoreThan1Minutes(@Param("currentDate1")LocalDateTime currentDate1, @Param("currentDate2")LocalDateTime currentDate2, @Param("currentDate3")LocalDateTime currentDate3);
}
