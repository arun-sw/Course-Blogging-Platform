package com.course_blogging.blog_service.controller;
import com.course_blogging.blog_service.exception.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<Map<String,String>> notFound(ResourceNotFoundException e) {
        return error(HttpStatus.NOT_FOUND,e.getMessage());
    }
    @ExceptionHandler(ForbiddenOperationException.class)
    ResponseEntity<Map<String,String>> forbidden(ForbiddenOperationException e) {
        return error(HttpStatus.FORBIDDEN,e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
    @ExceptionHandler({IllegalArgumentException.class,MethodArgumentNotValidException.class})
    ResponseEntity<Map<String,String>> badRequest(Exception e) { return error(HttpStatus.BAD_REQUEST,"Invalid request"); }
    private ResponseEntity<Map<String,String>> error(HttpStatus status,String message) {
        return ResponseEntity.status(status).body(Map.of("message",message));
    }
}
