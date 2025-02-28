package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulesRepo extends JpaRepository<Schedules, Integer> {
}
