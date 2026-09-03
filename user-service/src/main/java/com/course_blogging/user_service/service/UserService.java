package com.course_blogging.user_service.service;

import com.course_blogging.user_service.DTO.AutheticationResponce;
import com.course_blogging.user_service.DTO.LoginRequest;
import com.course_blogging.user_service.entity.UserEntity;
import com.course_blogging.user_service.repository.UserRepository;
import com.course_blogging.user_service.security.JWTService;
import com.course_blogging.user_service.exception.DuplicateResourceException;
import com.course_blogging.user_service.exception.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;
    // Create User
    public UserEntity CreateUser(UserEntity user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email is already registered");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(passwordEncoder.encode(user.getPassword())        );
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        userEntity.setBio(user.getBio());
        return userRepository.save(userEntity);
    }
    // Get All Users
    public List<UserEntity> GetAllUser() {
        return userRepository.findAll();
    }

    // Get User By ID
    public UserEntity GetUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->new ResourceNotFoundException("User not found: " + userId)
                );
    }
    // Update User
    public UserEntity UpdateUser(
            UserEntity user,
            Long userId) {
        UserEntity userEntity =GetUserById(userId);
        if (!userEntity.getEmail().equals(user.getEmail())&& userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email is already registered");
        }
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setBio(user.getBio());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(userEntity);
    }
    // Delete User
    public void DeleteUser(Long userId) {
        userRepository.delete(GetUserById(userId));
    }
    // Login
    public AutheticationResponce Login(
            LoginRequest loginRequest) {
        UserEntity userEntity =userRepository.findByemail(loginRequest.getEmail())
                        .orElseThrow(() ->new BadCredentialsException("Invalid email or password"));
        if (!passwordEncoder.matches(loginRequest.getPassword(),userEntity.getPassword()))
        {
            throw new BadCredentialsException(
                    "Invalid email or password"
            );
        }
        return new AutheticationResponce(
                jwtService.GenerateToken(userEntity),
                "bearer",
                userEntity.getUserId()
        );
    }
}