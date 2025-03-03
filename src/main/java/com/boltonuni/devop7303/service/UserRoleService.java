package com.boltonuni.devop7303.service;

import com.boltonuni.devop7303.entity.Role;
import com.boltonuni.devop7303.entity.User;
import com.boltonuni.devop7303.entity.UserRole;
import com.boltonuni.devop7303.repository.RoleRepo;
import com.boltonuni.devop7303.repository.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleRepo userRoleRepo;
    @Autowired
    RoleService roleRepo;
    public UserRole findByRoleId(int id){
        return userRoleRepo.findById(id).orElse(null);
    }

    public UserRole findByUserId(String userId){
        return Optional.of(userRoleRepo.findByUserId(userId)).orElse(null);
    }

    public UserRole saveUserRole(User user){
        Role role = roleRepo.findRoleByName("USER");
        System.out.println("Role...........");
        System.out.println(role);
        UserRole roleToUser = new UserRole();
        roleToUser.setUserId(user.getId());
        roleToUser.setRoleId(role.getId());
        roleToUser.setDateCreated(LocalDateTime.now());
        return userRoleRepo.save(roleToUser);
    }

    public Role getRole(int roleId){
        return roleRepo.findRoleById(roleId);
    }
}
