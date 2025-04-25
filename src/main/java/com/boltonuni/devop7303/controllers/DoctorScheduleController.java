package com.boltonuni.devop7303.controllers;

import com.boltonuni.devop7303.entity.DoctorScheduleDetail;
import com.boltonuni.devop7303.entity.Schedules;
import com.boltonuni.devop7303.entity.User;
import com.boltonuni.devop7303.models.Response;
import com.boltonuni.devop7303.service.DoctorScheduleService;
import com.boltonuni.devop7303.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/api/schedules")
public class DoctorScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Operation(summary = "Get patient's prescription")
    @ApiResponses(value =
            {@ApiResponse(
                    responseCode = "00",
                    description = "Success",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Schedules.class)
                    ) }), @ApiResponse(responseCode = "99", description = "Error Occurred",content = @Content) })
    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getPatientPrescriptionById(@PathVariable String userId, Principal principal) {
        return scheduleService.getPatientPrescription(principal.getName(), userId);
    }
}
