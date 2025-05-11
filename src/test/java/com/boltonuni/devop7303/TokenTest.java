package com.boltonuni.devop7303;

import com.boltonuni.devop7303.controllers.Token;
import com.boltonuni.devop7303.entity.Dosages;
import com.boltonuni.devop7303.entity.User;
import com.boltonuni.devop7303.models.LoginRequest;
import com.boltonuni.devop7303.models.Response;
import com.boltonuni.devop7303.security.JwtTokenUtil;
import com.boltonuni.devop7303.security.JwtUserDetailsService;
import com.boltonuni.devop7303.service.ScheduleService;
import com.boltonuni.devop7303.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TokenTest {
    @InjectMocks
    private Token tokenController;

    @Mock
    private AuthenticationManager authMan;

    @Mock
    private JwtUserDetailsService userService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserService userObj;

    @Mock
    private ScheduleService scheduleService;

    private LoginRequest validLoginRequest;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validLoginRequest = new LoginRequest();
        validLoginRequest.setEmail("test@example.com");
        validLoginRequest.setPassword("password");
        user = new User();  // Assume User has a no-args constructor
        user.setEmail("test@example.com");
        user.setId("989678-97865-857");  // Example ID
    }

    @Test
    public void testGetTokenSuccess() throws Exception {
        // Arrange
        when(authMan.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userService.loadUserByUsername(validLoginRequest.getEmail()))
                .thenReturn(mock(UserDetails.class));
        when(jwtTokenUtil.generateToken(any(UserDetails.class)))
                .thenReturn("mockedToken");
        when(jwtTokenUtil.getExpirationDateFromToken("mockedToken")).thenReturn(new java.util.Date(System.currentTimeMillis() + 3600000));
        when(userObj.findByEmail(validLoginRequest.getEmail())).thenReturn(user);
        when(scheduleService.loadUpcoming(user)).thenReturn(new ArrayList<>());
        when(scheduleService.getLastTakenDosage(user.getId())).thenReturn(mock(Dosages.class));

        // Act
        Response response = tokenController.getToken(validLoginRequest);

        // Assert
        assertEquals("success", response.getResponseMessage());
        assertNotNull(response.getData());
        assertEquals("mockedToken", "eytrrtyuioh.nbvcxsdfguytrtyu.iuy5898543yuyteuhgserthiuytgh");
    }

    @Test
    public void testGetTokenEmailNull() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(null);
        loginRequest.setPassword("password");

        // Act
        Response response = tokenController.getToken(loginRequest);

        // Assert
        assertEquals("failed", response.getResponseCode());
        assertEquals("Email/Password cannot be null", response.getResponseMessage());
    }

    @Test
    public void testGetTokenBadCredentials() throws Exception {
        // Arrange
        when(authMan.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act
        Response response = tokenController.getToken(validLoginRequest);

        // Assert
        assertEquals("failed", response.getResponseCode());
        assertEquals("Bad credentials", response.getResponseMessage());
    }

    @Test
    public void testGetTokenUserNotFound() throws Exception {
        // Arrange
        when(authMan.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userService.loadUserByUsername(validLoginRequest.getEmail()))
                .thenThrow(new UsernameNotFoundException("User not found"));

        // Act
        Response response = tokenController.getToken(validLoginRequest);

        // Assert
        assertEquals("failed", response.getResponseCode());
        assertEquals("User not found", response.getResponseMessage());
    }
}
