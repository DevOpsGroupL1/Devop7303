package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.DoctorHistory;
import com.boltonuni.devop7303.entity.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoctorPrescriptionRepository extends JpaRepository<Schedules, Integer> {

    @Query(value = "SELECT * from Schedules p where p.doctor_id = :doctorId", nativeQuery = true)
    List<Schedules> loadDoctorPrescriptionHistory(@Param("doctorId") String doctorId);
}
