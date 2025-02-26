package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {
    UserRole findByUserId(int userId);
}
