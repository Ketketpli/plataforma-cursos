package com.example.cursos_backend.controller;

import com.example.cursos_backend.dtos.LoginRequestDTO;
import com.example.cursos_backend.dtos.LoginResponseDTO;
import com.example.cursos_backend.dtos.RegisterRequestDTO;
import com.example.cursos_backend.dtos.RegisterResponseDTO;
import com.example.cursos_backend.services.LoginUserService;
import com.example.cursos_backend.services.RegisterUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserAuthController {

    private final RegisterUserService registerUserService;
    private final LoginUserService loginUserService;

    @PostMapping(value = "/register")
    public ResponseEntity<RegisterResponseDTO> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        RegisterResponseDTO newUser = registerUserService.registerUser(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        String userToken = loginUserService.loginUser(loginRequestDTO);
        LoginResponseDTO logUser = new LoginResponseDTO(userToken);
        return ResponseEntity.status(HttpStatus.OK).body(logUser);
    }
}
