package com.boltonuni.devop7303.controllers;

import com.boltonuni.devop7303.entity.User;
import com.boltonuni.devop7303.models.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/api/")
@CrossOrigin
public class UserReporting {


    @Operation(summary = "Returns Patient overall report")
    @ApiResponses(value =
            {@ApiResponse(
                    responseCode = "00",
                    description = "Success",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    ) }), @ApiResponse(responseCode = "99", description = "Error Occurred",content = @Content) })
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "patient/report")
    public Response getReports(Principal principal){
        System.out.println("email: "+principal.getName());
        return null;
    }
}
