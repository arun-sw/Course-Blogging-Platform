package com.course_blogging.user_service.security;

import io.jsonwebtoken.Claims;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    public JwtAuthenticationFilter(JWTService jwtService,UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader =request.getHeader("Authorization");
        // Checks the No JWT token
        if (authorizationHeader == null ||!authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.substring(7);
        try {
            // Read JWT claims
            Claims claims = jwtService.claims(token);
            String email = claims.getSubject();
            if (email != null &&SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user
                UserDetails userDetails =userDetailsService.loadUserByUsername(email);
                // Create authentication
                UsernamePasswordAuthenticationToken authentication =new UsernamePasswordAuthenticationToken
                        (       userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Store authentication
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("JWT Authentication failed: "+ e.getMessage());
            e.printStackTrace();
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}