package com.example.cursos_backend.dtos;

public record ReviewResponseDTO(Long id,
                                String studentName,
                                Integer rating,
                                String comment) {}
