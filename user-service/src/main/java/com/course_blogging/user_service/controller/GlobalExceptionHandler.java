package com.course_blogging.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String ,Object> BadRequest(IllegalArgumentException exception){
        return Map.of("message",exception.getMessage());
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleAllRemainingExceptions(Exception exception) {
        // Optional: Log the exception here using a logger so you can debug later
        return Map.of(
                "message", "An unexpected error occurred on the server.",
                "error", exception.getClass().getSimpleName()
        );
    }
}
