package com.boltonuni.devop7303.service;

import com.boltonuni.devop7303.entity.Role;
import com.boltonuni.devop7303.entity.User;
import com.boltonuni.devop7303.entity.UserDetail;
import com.boltonuni.devop7303.entity.UserRole;
import com.boltonuni.devop7303.models.Response;
import com.boltonuni.devop7303.repository.UserRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    @Autowired
    EmailService emailService;


    public User findByEmail(String email){
        return userRepo.findByEmail(email);
    }

    public User findById(String userId){
        return userRepo.findById(userId);
    }

    public Response getUsers(String email){
        List<User> users = userRepo.findByIsActiveTrueAndEmailNot(email);
        users = users.stream().map((user)->{
            UserRole userRole = userRoleService.findByUserId(user.getId());
            Role role = userRoleService.getRole(userRole.getRoleId());
            User user1 = user;
            user1.setUserRole(role);
            return user1;
        }).collect(Collectors.toList());
        return new Response("success", "00", users);
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

            emailService.sendAccountVerificationMail(savedUser.getfName(), savedUser.getId(), savedUser.getEmail(), "Account Verification");

            return new Response("Account Registration Successful", "00", savedUser);
        }catch (Throwable th){
            th.printStackTrace();
            LOGGER.debug("User registration: ", th);
            return new Response("User registration Error occurred","99", th.getMessage());
        }

    }

    public Response activateRegister(String userId){
        try{
            User user = userRepo.findById(userId);
            if(user==null){
                return new Response("Account does not exist", "99",null);
            }
            if(user.isActive()){
                return new Response("Account already activated", "99",null);
            }
            user.setActive(true);
            userRepo.save(user);
            return new Response("Success", "00", null);
        }catch (Throwable th){
            LOGGER.debug("RegisterUser", th);
            return new Response("User does not exist", "99",null);
        }
    }
}
