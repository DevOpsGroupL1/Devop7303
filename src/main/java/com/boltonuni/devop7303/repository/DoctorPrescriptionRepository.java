package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.DoctorHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoctorPrescriptionRepository extends JpaRepository<DoctorHistory, Long> {

    @Query(value = "SELECT * from prescriptions p where p.doctor_id = :doctorId", nativeQuery = true)
    List<DoctorHistory> loadDoctorPrescriptionHistory(@Param("doctorId") Long doctorId);
}
