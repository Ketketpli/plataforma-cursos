package com.example.cursos_backend.services;

import com.example.cursos_backend.dtos.RegisterRequestDTO;
import com.example.cursos_backend.dtos.RegisterResponseDTO;
import com.example.cursos_backend.enums.Role;
import com.example.cursos_backend.exceptions.EmailAlreadyExistsException;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponseDTO registerUser(RegisterRequestDTO registerRequestDTO) {

        if(userRepository.existsByEmail(registerRequestDTO.email())) {
            throw new EmailAlreadyExistsException();
        }

        User user = new User();
        user.setRole(Role.USER);
        user.setName(registerRequestDTO.name());
        user.setEmail(registerRequestDTO.email());

        String encodedPassword = passwordEncoder.encode(registerRequestDTO.password());
        user.setPassword(encodedPassword);

        User newUser = userRepository.save(user);

        return new RegisterResponseDTO(
                newUser.getId(),
                newUser.getName(),
                newUser.getEmail(),
                newUser.getRole());
    }
}
