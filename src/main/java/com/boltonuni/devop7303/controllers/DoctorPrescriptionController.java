package com.boltonuni.devop7303.controllers;

import com.boltonuni.devop7303.entity.Schedules;
import com.boltonuni.devop7303.entity.User;
import com.boltonuni.devop7303.models.Response;
import com.boltonuni.devop7303.service.DoctorPrescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/")
public class DoctorPrescriptionController {

    private final DoctorPrescriptionService doctorPrescriptionService;

    @Autowired
    public DoctorPrescriptionController(DoctorPrescriptionService doctorPrescriptionService) {
        this.doctorPrescriptionService = doctorPrescriptionService;
    }

    //Get Doctors prescriptions history
    @Operation(summary = "Get Doctor's prescriptions history")
    @ApiResponses(value =
            {@ApiResponse(
                    responseCode = "00",
                    description = "Success",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    ) }), @ApiResponse(responseCode = "99", description = "Error Occurred",content = @Content) })
    @GetMapping("/doctor-prescriptions/{doctorId}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public Response getDoctorPrescriptionHistory(@PathVariable String doctorId) {
        List<Schedules> prescriptions = doctorPrescriptionService.getDoctorPrescriptionHistory(doctorId);

        if (prescriptions.isEmpty()) {
            return new Response("00", "No prescriptions found", prescriptions);
        }
        return new Response("00", "Success", prescriptions);
    }
}

