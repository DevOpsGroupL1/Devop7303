package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.DoctorHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorHistoryRepository extends JpaRepository<DoctorHistory, Long> {

    @Query(value = "SELECT * FROM prescriptions WHERE doctor_id = :doctorId", nativeQuery = true)
    List<DoctorHistory> loadDoctorHistory(@Param("doctorId") String doctorId);
}


