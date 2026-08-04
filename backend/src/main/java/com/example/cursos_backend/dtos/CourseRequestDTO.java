package com.example.cursos_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Set;

public record CourseRequestDTO(@NotBlank String name,
                               @NotBlank String description,
                               @NotNull @Positive BigDecimal price,
                               @NotNull @Positive Integer duration,
                               @NotNull Set<Long> categoryIds) {}
