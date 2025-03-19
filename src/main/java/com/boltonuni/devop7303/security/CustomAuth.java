package com.boltonuni.devop7303.security;

import com.boltonuni.devop7303.entity.Role;
import com.boltonuni.devop7303.entity.User;
import com.boltonuni.devop7303.entity.UserRole;
import com.boltonuni.devop7303.service.RoleService;
import com.boltonuni.devop7303.service.UserRoleService;
import com.boltonuni.devop7303.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuth implements AuthenticationProvider {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Auth");
        User user = userService.findByEmail(authentication.getName());
        if (user==null)
            throw new UsernameNotFoundException("Account not found");
        if(!user.isActive())
            throw new DisabledException("This account is not verified");
        String pass = (String) authentication.getCredentials();
//        System.out.println(passwordEncoder.matches(pass, user.getPassword()));
        if (!passwordEncoder.matches(pass, user.getPassword())) {
            System.out.println("..................On lockout things");
            throw new BadCredentialsException("Invalid username/password");
        }

        Role role = null;
        if(user.getUserDetail()!=null){
            UserRole userRole = userRoleService.findByUserId(user.getId());
            role = roleService.findRoleById(userRole.getRoleId());
        }else if(user.getDoctorDetail()!=null){
            UserRole userRole = userRoleService.findByUserId(user.getId());
            role = roleService.findRoleById(userRole.getRoleId());
        }
        System.out.println(role);
        System.out.println(role.getRoleName());
        List<GrantedAuthority> roleAuth = new ArrayList<>();
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role.getRoleName());
        roleAuth.add(auth);
        System.out.println("Setting role..");

        UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(authentication.getName(), user.getPassword(),roleAuth);
        System.out.println("Setting User Session");

        System.out.println("Login success: "+user.getEmail());
        return userAuth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
