package com.boltonuni.devop7303.controllers;

import com.boltonuni.devop7303.entity.User;
import com.boltonuni.devop7303.models.LoginRequest;
import com.boltonuni.devop7303.models.Response;
import com.boltonuni.devop7303.security.JwtTokenUtil;
import com.boltonuni.devop7303.security.JwtUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/v1/api/")
public class Login {
    private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);
    @Autowired
    private AuthenticationManager authMan;
    @Autowired
    JwtUserDetailsService userService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping(value = "test")
    public String test(){
        return "Hey dear, welcome";
    }

    @PostMapping(value = "token")
    public Response getToken(@RequestBody LoginRequest login){
        try {
            Objects.requireNonNull(login.getEmail());
            Objects.requireNonNull(login.getPassword());
            initAuth(login);
            final UserDetails userDetails =  userService.loadUserByUsername(login.getEmail());
            String token = jwtTokenUtil.generateToken(userDetails);
            final long expiry = jwtTokenUtil.getExpirationDateFromToken(token).getTime();
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("expiry", expiry);
            return new Response("success", "00",response);

        }catch (NullPointerException n){
            return new Response("failed", "99","Email/Password cannot be null");
        } catch (Exception e) {
            return new Response("failed", "98",e.getMessage());
        }
    }

    private void initAuth(LoginRequest loginRequest) throws Exception{
        try{
            authMan.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        }catch (DisabledException dis){
            throw new Exception(dis.getMessage());
        }catch (UsernameNotFoundException us){
            throw new Exception(us.getMessage());
        }catch (BadCredentialsException bc){
            throw new Exception(bc.getMessage());
        } catch (Exception e){
            LOGGER.error("initAuth error: ",e);
           throw new Exception("Try again");
        }
    }
}
