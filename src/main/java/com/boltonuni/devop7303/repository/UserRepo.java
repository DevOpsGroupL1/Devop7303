package com.boltonuni.devop7303.repository;

import com.boltonuni.devop7303.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findById(String userId);
    List<User> findByIsActiveTrueAndEmailNot(String email);

}
