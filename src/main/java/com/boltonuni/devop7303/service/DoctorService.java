package com.boltonuni.devop7303.service;

import com.boltonuni.devop7303.entity.*;
import com.boltonuni.devop7303.models.Response;
import com.boltonuni.devop7303.repository.UserRepo;
import jakarta.validation.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DoctorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorService.class);
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleService roleService;
    @Autowired
    EmailService emailService;

    public Response doctorRegistration(User user){
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

            DoctorDetail doctorDetail = user.getDoctorDetail();
            doctorDetail.setUser(user);
            user.setDoctorDetail(doctorDetail);

            User savedUser = userRepo.save(user);
            System.out.println("...................");
            System.out.println(savedUser);
            UserRole userRole = userRoleService.saveDoctorRole(savedUser);
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
}
