package com.boltonuni.devop7303.service;

import com.boltonuni.devop7303.entity.Role;
import com.boltonuni.devop7303.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;

    public Role findRoleById(int roleId){
        return roleRepo.findById(roleId).orElse(null);
    }
}
