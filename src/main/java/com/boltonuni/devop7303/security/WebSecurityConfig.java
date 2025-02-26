package com.boltonuni.devop7303.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    AuthenticationManager authenticationManager;
    @Autowired
    CustomAuth customAuth;
    @Autowired
    AuthEntryPoint entryPoint;
    @Autowired
    JwtRequestFilter requestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuth);

        http.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/v1/api/token",
                                "/v1/api/test",
                                "/devop7303/webjars/**",
                                "/devop7303/swagger-ui/**",
                                "/devop7303/swagger-ui",
                                "/devop7303/swagger-ui.html",
                                "/devop7303/v3/api-docs",
                                "/devop7303/v3/api-docs/**",
                                "/devop7303/swagger-resources/**",
                                "/devop7303/webjars/**",
                                "/actuator",
                                "/actuator/**",
                                "/devop7303/actuator",
                                "/devop7303/actuator/**"
                        )
                        .permitAll()
                        .anyRequest().authenticated()).exceptionHandling(u->u.authenticationEntryPoint(entryPoint)).sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
