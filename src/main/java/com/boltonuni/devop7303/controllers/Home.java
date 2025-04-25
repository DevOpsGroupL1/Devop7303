package com.boltonuni.devop7303.controllers;

import com.boltonuni.devop7303.entity.Role;
import com.boltonuni.devop7303.entity.Schedules;
import com.boltonuni.devop7303.entity.User;
import com.boltonuni.devop7303.entity.UserRole;
import com.boltonuni.devop7303.models.Response;
import com.boltonuni.devop7303.service.RoleService;
import com.boltonuni.devop7303.service.ScheduleService;
import com.boltonuni.devop7303.service.UserRoleService;
import com.boltonuni.devop7303.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/v1/api/")
@CrossOrigin
public class Home {
    @Autowired
    private UserService userService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    //This is to retrieve all Patients to the doctor.
    @Operation(summary = "Get Active users")
    @ApiResponses(value =
            {@ApiResponse(
                    responseCode = "00",
                    description = "Success",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    ) }), @ApiResponse(responseCode = "99", description = "Error Occurred",content = @Content) })
    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getUsers(Principal principal){
        System.out.println("email: "+principal.getName());
        return userService.getUsers(principal.getName());
    }


    //This saves schedule set by a Doctor to a patient
    @Operation(summary = "Schedule a prescription to a patient")
    @ApiResponses(value =
            {@ApiResponse(
                    responseCode = "00",
                    description = "Success",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    ) }), @ApiResponse(responseCode = "99", description = "Error Occurred",content = @Content) })
    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(value = "doctor/schedule")
    public Response saveSchedule(@RequestBody @Valid Schedules schedules){
        System.out.println("Schedule: "+schedules);
        if(schedules.getUserId()==null || schedules.getUserId().isBlank())
            return new Response("Failed", "99", "User ID required");
        return scheduleService.saveSchedule(schedules);
    }


    //Get Last Patients' prescriptions
    @PreAuthorize("hasAnyAuthority('USER', 'DOCTOR')")
    @Operation(summary = "Get Last Prescriptions base on Role")
    @ApiResponses(value =
            {@ApiResponse(
                    responseCode = "00",
                    description = "Success",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    ) }), @ApiResponse(responseCode = "99", description = "Error Occurred",content = @Content) })
    @GetMapping(value = "dashboard")
    public Response patientDashboard(Principal principal){
        User user = userService.findByEmail(principal.getName());
        if(user==null)
            return new Response("Account does not exist", "99",null);
        UserRole userAppRole = userRoleService.findByUserId(user.getId());
        if(userAppRole==null){
            return new Response("Success", "00", new ArrayList<>());
        }
        Role userRole = roleService.findRoleById(userAppRole.getRoleId());
        if(userRole==null){
            return new Response("Success", "00", new ArrayList<>());
        }

        if(userRole.getRoleName().equalsIgnoreCase("user")) {
            System.out.println("Entered user");
            return scheduleService.getLast10Schedule(user.getId());
        }

        if(userRole.getRoleName().equalsIgnoreCase("doctor")) {
            System.out.println("Entered doctor");
            return scheduleService.getLast50Schedule(user.getId());
        }
        return new Response("Success", "00", new ArrayList<>());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping(value="dosage/{dosageId}/schedule/{schedId}")
    public Response tickDosage(@PathVariable int dosageId, @PathVariable String schedId){
        System.out.println("Dosage: "+dosageId);
        System.out.println("Schedule: "+schedId);
        return scheduleService.updateDosageIntake(dosageId, schedId);
    }



}
