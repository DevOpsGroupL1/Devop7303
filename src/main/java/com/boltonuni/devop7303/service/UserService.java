package com.boltonuni.devop7303.service;

import com.boltonuni.devop7303.entity.User;
import com.boltonuni.devop7303.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User findByEmail(String email){
        return userRepo.findByEmail(email);
    }
}
