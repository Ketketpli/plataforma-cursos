package com.example.cursos_backend.dtos;

import java.time.LocalDateTime;

public record EnrollResponseDTO(Long id,
                                Long courseId,
                                Long studentId,
                                LocalDateTime enrollAt) {}
