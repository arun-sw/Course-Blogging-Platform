package com.course_blogging.user_service.controller;

import com.course_blogging.user_service.entity.UserEntity;
import com.course_blogging.user_service.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Manage registered user profiles")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    @Autowired
    private UserService userService;
    // Get All Users
    @GetMapping
    @Operation(summary = "List users", description = "Returns every registered user.")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    public List<UserEntity> GetAllUser() {

        return userService.GetAllUser();
    }
    // Get User By ID
    @GetMapping("/{userId}")
    @Operation(summary = "Get a user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public UserEntity GetUserById(
            @Parameter(description = "ID of the user to retrieve", required = true, example = "1", in = ParameterIn.PATH)
            @PathVariable Long userId) {

        return userService.GetUserById(userId);
    }
    // Update User
    @PatchMapping("/{userId}")
    @Operation(summary = "Update a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public UserEntity UpdateUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User fields to update", required = true,
                    content = @Content(schema = @Schema(implementation = UserEntity.class)))
            @Valid @RequestBody UserEntity user,
            @Parameter(description = "ID of the user to update", required = true, example = "1", in = ParameterIn.PATH)
            @PathVariable Long userId) {
        return userService.UpdateUser(user, userId);
    }
    // Delete User
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public String DeleteUser(
            @Parameter(description = "ID of the user to delete", required = true, example = "1", in = ParameterIn.PATH)
            @PathVariable Long userId) {
        userService.DeleteUser(userId);

        return "User was deleted successfully";
    }
}
