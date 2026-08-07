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

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> invalidCredentials(InvalidCredentialsException invalidCredentials) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(invalidCredentials.getMessage());
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<String> categoryAlreadyExist(CategoryAlreadyExistsException categoryAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(categoryAlreadyExistsException.getMessage());
    }

    @ExceptionHandler(ValueNotFoundException.class)
    public ResponseEntity<String> valueNotFoundException(ValueNotFoundException valueNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(valueNotFoundException.getMessage());
    }

    @ExceptionHandler(InvalidAccessException.class)
    public ResponseEntity<String> invalidAccessException(InvalidAccessException invalidAccessException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(invalidAccessException.getMessage());
    }

}
