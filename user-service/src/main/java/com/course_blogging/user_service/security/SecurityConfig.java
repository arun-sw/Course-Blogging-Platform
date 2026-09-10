package com.course_blogging.user_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    // Password Encoder
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // Security Filter Chain
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtFilter ,OAuth2SuccessHandler successHandler)
            throws Exception {
        return http
                // Disable CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // Stateless Session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Authorization Rules
                .authorizeHttpRequests(auth ->
                        auth
                                // Auth APIs are set public
                                .requestMatchers("/auth/**").permitAll()
                                // oauth api are set public
                                .requestMatchers("/oauth/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/users/*").permitAll()
                                // Swagger permission
                                  .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                // All other APIs want to JWT token
                                .anyRequest().authenticated()
                )
                // Oauth login
                .oauth2Login(oauth -> oauth
                        .successHandler((AuthenticationSuccessHandler) successHandler)
                )
                // Add JWT filter
                .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
