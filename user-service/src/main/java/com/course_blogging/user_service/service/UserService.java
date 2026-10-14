package com.course_blogging.user_service.service;

import com.course_blogging.user_service.DTO.AutheticationResponce;
import com.course_blogging.user_service.DTO.LoginRequest;
import com.course_blogging.user_service.entity.AuthType;
import com.course_blogging.user_service.entity.UserEntity;
import com.course_blogging.user_service.event.UserCreatedEvent;
import com.course_blogging.user_service.event.UserUpdateEvent;
import com.course_blogging.user_service.producer.UserProducer;
import com.course_blogging.user_service.repository.UserRepository;
import com.course_blogging.user_service.security.JWTService;
import com.course_blogging.user_service.exception.DuplicateResourceException;
import com.course_blogging.user_service.exception.ResourceNotFoundException;


import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private  UserProducer userProducer;

    private static final Logger logger = LogManager.getLogger(UserService.class);
    // Create User
    public UserEntity CreateUser(UserEntity user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email is already registered");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(passwordEncoder.encode(user.getPassword())  );
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        userEntity.setBio(user.getBio());
        userEntity.setAuthType(AuthType.NORMAL);
        logger.info("user created successfully");
        UserEntity saveduser=userRepository.save(userEntity);

        UserCreatedEvent event=new UserCreatedEvent();
        event.setUserId(saveduser.getUserId());
        event.setName(saveduser.getName());
        event.setEmail(saveduser.getEmail());

        userProducer.sendUserCreated(event);
        return saveduser;
    }
    // Get All Users
    public List<UserEntity> GetAllUser() {
        return userRepository.findAll();
    }

    // Get User By ID
    public UserEntity GetUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("User not found: " + userId));
    }
    // Update User
    public UserEntity UpdateUser(UserEntity user,Long userId) {
        UserEntity userEntity =GetUserById(userId);
        if (!userEntity.getEmail().equals(user.getEmail())&& userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email is already registered");
        }
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setBio(user.getBio());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("user Update successfully");
        UserEntity updateduser=userRepository.save(userEntity);

        UserUpdateEvent event=new UserUpdateEvent();
        event.setUserId(updateduser.getUserId());
        event.setName(updateduser.getName());
        event.setEmail(updateduser.getEmail());
        userProducer.sendUserUpdate(event);
        return updateduser;
    }
    // Delete User
    public void DeleteUser(Long userId) {

        logger.info("user created deleted successfully");
        userRepository.delete(GetUserById(userId));
    }
    // Login
    public AutheticationResponce Login(
            LoginRequest loginRequest) {
        UserEntity userEntity =userRepository.findByemail(loginRequest.getEmail())
                        .orElseThrow(() ->new BadCredentialsException("Invalid email or password"));
        if (!passwordEncoder.matches(loginRequest.getPassword(),userEntity.getPassword()))
        {
            throw new BadCredentialsException("Invalid email or password");
        }
        logger.info("user {} was login successfully",loginRequest.getEmail());
        return new AutheticationResponce(
                jwtService.GenerateToken(userEntity),
                "bearer",
                userEntity.getUserId()
        );
    }
}