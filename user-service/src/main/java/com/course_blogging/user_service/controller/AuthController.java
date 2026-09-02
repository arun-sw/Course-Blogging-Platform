package com.course_blogging.user_service.controller;

import com.course_blogging.user_service.DTO.AutheticationResponce;
import com.course_blogging.user_service.DTO.LoginRequest;
import com.course_blogging.user_service.entity.UserEntity;
import com.course_blogging.user_service.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public UserEntity CreateUser(@RequestBody UserEntity user){
        return userService.CreateUser(user);
    }
    @SneakyThrows
    @PostMapping("login")
    public AutheticationResponce Login(@RequestBody LoginRequest loginRequest){
        return userService.Login(loginRequest);
    }
}
