package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.DoctorHistory;
import com.boltonuni.devop7303.entity.Schedules;
import com.boltonuni.devop7303.repository.DoctorHistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoctorHistoryRepository extends JpaRepository<Schedules, Integer> {
    @Query(value = "SELECT * from Schedules s where doctor_id = ?",nativeQuery = true)
    List<Schedules> loadDoctorHistory(@Param("doctorId") String doctorId);
}

