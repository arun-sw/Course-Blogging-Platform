package com.course_blogging.user_service.controller;

import com.course_blogging.user_service.DTO.AutheticationResponce;
import com.course_blogging.user_service.DTO.LoginRequest;
import com.course_blogging.user_service.entity.UserEntity;
import com.course_blogging.user_service.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    // Register
    @PostMapping("/register")
    public UserEntity CreateUser(@Valid @RequestBody UserEntity user) {
        return userService.CreateUser(user);
    }
    // Login
    @PostMapping("/login")
    public AutheticationResponce Login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.Login(loginRequest);
    }
}