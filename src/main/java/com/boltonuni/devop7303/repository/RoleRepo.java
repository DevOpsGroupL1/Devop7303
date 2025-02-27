package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    public Role findByRoleName(String roleName);
}
