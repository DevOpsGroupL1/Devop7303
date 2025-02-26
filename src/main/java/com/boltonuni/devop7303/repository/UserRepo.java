package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
}
