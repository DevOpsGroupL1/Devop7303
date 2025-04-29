package com.boltonuni.devop7303.controllers;

import com.boltonuni.devop7303.entity.User;
import com.boltonuni.devop7303.models.Response;
import com.boltonuni.devop7303.service.DoctorHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescriptions")
public class DoctorHistoryController {

    @Autowired
    private DoctorHistoryService doctorHistoryService;

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
    @GetMapping("/doctor/{doctorId}")
    public Response getPrescriptionsByDoctor(@PathVariable String doctorId) {
        return doctorHistoryService.getPrescriptionsByDoctor(doctorId);
    }
}
