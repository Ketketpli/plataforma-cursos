package com.example.cursos_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> emailAlreadyExist(EmailAlreadyExistsException emailAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(emailAlreadyExistsException.getMessage());
    }
}
