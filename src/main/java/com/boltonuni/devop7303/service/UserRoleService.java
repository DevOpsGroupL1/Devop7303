package com.boltonuni.devop7303.service;

import com.boltonuni.devop7303.entity.UserRole;
import com.boltonuni.devop7303.repository.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleRepo userRoleRepo;

    public UserRole findByRoleId(int id){
        return userRoleRepo.findById(id).orElse(null);
    }

    public UserRole findByUserId(int userId){
        return userRoleRepo.findById(userId).orElse(null);
    }
}
