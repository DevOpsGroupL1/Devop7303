package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepo extends JpaRepository<UserDetail, Integer> {
}
