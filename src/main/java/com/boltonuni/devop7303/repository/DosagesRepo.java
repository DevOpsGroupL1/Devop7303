package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.Dosages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DosagesRepo extends JpaRepository<Dosages, Integer> {
}
