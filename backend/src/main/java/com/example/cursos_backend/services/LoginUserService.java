package com.example.cursos_backend.services;

import com.example.cursos_backend.dtos.LoginRequestDTO;
import com.example.cursos_backend.exceptions.InvalidCredentialsException;
import com.example.cursos_backend.infra.TokenService;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public String loginUser(LoginRequestDTO loginRequestDTO) {

        User userAccount = userRepository.findByEmail(loginRequestDTO.email())
                .orElseThrow(InvalidCredentialsException::new);

        boolean correctPassword = passwordEncoder.matches(loginRequestDTO.password(), userAccount.getPassword());

        if (!correctPassword) {
            throw new InvalidCredentialsException();
        }

        return tokenService.generateToken(userAccount);
    }
}
