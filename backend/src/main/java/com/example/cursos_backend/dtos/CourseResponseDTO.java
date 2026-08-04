package com.example.cursos_backend.dtos;

import java.math.BigDecimal;
import java.util.Set;

public record CourseResponseDTO(Long id,
                                String name,
                                String description,
                                BigDecimal price,
                                Integer duration,
                                String instructorName,
                                Set<String> categoryNames) {}
