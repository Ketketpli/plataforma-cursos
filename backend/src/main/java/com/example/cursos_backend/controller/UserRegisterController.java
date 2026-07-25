package com.example.cursos_backend.controller;

import com.example.cursos_backend.dtos.RegisterRequestDTO;
import com.example.cursos_backend.dtos.RegisterResponseDTO;
import com.example.cursos_backend.services.RegisterUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserRegisterController {

    private final RegisterUserService registerUserService;

    @PostMapping(value = "/register")
    public ResponseEntity<RegisterResponseDTO> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        RegisterResponseDTO newUser = registerUserService.registerUser(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
