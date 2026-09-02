package com.course_blogging.user_service.controller;

import com.course_blogging.user_service.exception.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFound(ResourceNotFoundException e) {
        return error(HttpStatus.NOT_FOUND,e.getMessage());
    }
    // Duplicate Resource
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> duplicate(DuplicateResourceException e) {
        return error(HttpStatus.CONFLICT,e.getMessage());
    }
    // Invalid Login Credentials
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> unauthorized(
            BadCredentialsException e) {
        return error(HttpStatus.UNAUTHORIZED,e.getMessage());
    }
    // Validation Error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validation(
            MethodArgumentNotValidException e) {
        Map<String, String> fields = new LinkedHashMap<>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError ->
                        fields.put(
                                fieldError.getField(),
                                fieldError.getDefaultMessage()
                        )
                );
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Validation failed");
        body.put("errors", fields);
        return ResponseEntity.badRequest().body(body);
    }
    // Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> badRequest(IllegalArgumentException e) {
        return error(HttpStatus.BAD_REQUEST,e.getMessage());
    }
    // Unexpected Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> unexpected(Exception e) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR,"An unexpected server error occurred");
    }
    // Common Error Response
    private ResponseEntity<Map<String, Object>> error(HttpStatus status,String message) {
        return ResponseEntity.status(status).body(Map.of("message", message));
    }
}