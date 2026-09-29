package com.learn.demo.security;

import com.learn.demo.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    //               ↑
    // Guarantees this filter runs exactly ONCE per request
    // (Spring can sometimes call filters multiple times — this prevents that)

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Step 1: Extract Authorization header
        String authHeader = request.getHeader("Authorization");

        // Step 2: Check if header exists and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);  // no token → pass through
            return;                                    // Security config decides if allowed
        }

        // Step 3: Extract the actual token (remove "Bearer " prefix)
        String token = authHeader.substring(7);

        // Step 4: Validate token
        if (jwtUtil.isTokenValid(token)) {

            String username = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);

            // Step 5: Create authentication object and set in SecurityContext
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,       // credentials null — already authenticated via token
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );

            // Step 6: Tell Spring Security "this request is authenticated"
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Step 7: Continue the filter chain regardless
        filterChain.doFilter(request, response);
    }
}