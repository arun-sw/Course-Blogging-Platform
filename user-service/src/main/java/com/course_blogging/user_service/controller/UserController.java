package com.course_blogging.user_service.controller;

import com.course_blogging.user_service.entity.UserEntity;
import com.course_blogging.user_service.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    // Get All Users
    @GetMapping
    public List<UserEntity> GetAllUser() {

        return userService.GetAllUser();
    }
    // Get User By ID
    @GetMapping("/{userId}")
    public UserEntity GetUserById(@PathVariable Long userId) {

        return userService.GetUserById(userId);
    }
    // Update User
    @PatchMapping("/{userId}")
    public UserEntity UpdateUser(@Valid @RequestBody UserEntity user,@PathVariable Long userId) {
        return userService.UpdateUser(user, userId);
    }
    // Delete User
    @DeleteMapping("/{userId}")
    public String DeleteUser(@PathVariable Long userId) {
        userService.DeleteUser(userId);

        return "User was deleted successfully";
    }
}