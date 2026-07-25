package com.example.cursos_backend.dtos;

import com.example.cursos_backend.enums.Role;

public record RegisterResponseDTO (Long id,
                                   String name,
                                   String email,
                                   Role role) {}
