package com.boltonuni.devop7303.service;

import com.boltonuni.devop7303.entity.Role;
import com.boltonuni.devop7303.entity.User;
import com.boltonuni.devop7303.entity.UserDetail;
import com.boltonuni.devop7303.entity.UserRole;
import com.boltonuni.devop7303.models.Response;
import com.boltonuni.devop7303.repository.UserDetailRepo;
import com.boltonuni.devop7303.repository.UserRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {
    static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public User findByEmail(String email){
        return userRepo.findByEmail(email);
    }

    public Response userRegistration(User user){
        try {
            User existingUser = userRepo.findByEmail(user.getEmail());
            if(existingUser!=null){
                return new Response("Email already exist", "99", null);
            }
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
            user.setDateCreated(LocalDateTime.now());
            UUID id = UUID.randomUUID();
            user.setId(id.toString());

            UserDetail userDetail = user.getUserDetail();
            userDetail.setUser(user);
            user.setUserDetail(userDetail);

            User savedUser = userRepo.save(user);
            System.out.println("...................");
            System.out.println(savedUser);
            UserRole userRole = userRoleService.saveUserRole(savedUser);
            Role role = roleService.findRoleById(userRole.getRoleId());
            savedUser.setUserRole(role);

            return new Response("Account Registration Successful", "00", savedUser);
        }catch (Throwable th){
            th.printStackTrace();
            LOGGER.debug("User registration: ", th);
            return new Response("User registration Error occurred","99", th.getMessage());
        }

    }
}
