package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.DoctorHistory;
import com.boltonuni.devop7303.entity.Schedules;
import com.boltonuni.devop7303.repository.DoctorHistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoctorHistoryRepository extends JpaRepository<Schedules, Integer> {
    List<DoctorHistory> findByDoctorId;

    List<DoctorHistory> findDoctorById(String doctorId);
}

