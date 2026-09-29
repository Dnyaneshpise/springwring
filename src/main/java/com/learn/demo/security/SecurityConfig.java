package com.learn.demo.security;

import com.learn.demo.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // ↑ CSRF protection is for browser cookie-based auth
                //   JWT is stateless/header-based — CSRF not applicable, safe to disable

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // ↑ Tell Spring: "don't create HTTP sessions"
                //   JWT is stateless — we never want Spring creating sessions

                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/auth/**").permitAll()
                                // ↑ Login/register endpoints — must be public, no token needed

                                .requestMatchers("/api/users/**").hasAnyRole("JUNIOR", "SENIOR", "LEAD")
                                // ↑ Only authenticated users with these roles

                                .anyRequest().authenticated()
                        // ↑ Everything else requires authentication
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        // ↑ Register our JWT filter to run BEFORE Spring's default auth filter

        return http.build();
    }
}