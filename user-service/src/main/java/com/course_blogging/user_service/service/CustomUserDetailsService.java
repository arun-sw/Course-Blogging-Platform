package com.course_blogging.user_service.service;

import com.course_blogging.user_service.entity.UserEntity;
import com.course_blogging.user_service.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // Load User By Email
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        UserEntity user = userRepository
                .findByemail(email)
                .orElseThrow(() ->new UsernameNotFoundException("User not found with email: " + email));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}