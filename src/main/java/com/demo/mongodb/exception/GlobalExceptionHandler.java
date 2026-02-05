package com.demo.mongodb.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> handleRuntime(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of(
            "timestamp", LocalDateTime.now(),
            "message", ex.getMessage()
        ));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of(
            "timestamp", LocalDateTime.now(),
            "message", ex.getMessage()
        ));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage())
    );

    Map<String, Object> body = new HashMap<>();
    body.put("errors", errors);
    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Validation failed");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

}
