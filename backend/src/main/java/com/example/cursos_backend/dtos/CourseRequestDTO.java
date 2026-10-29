package com.example.cursos_backend.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Set;

public record CourseRequestDTO(@NotBlank @Size(max = 100) String name,
                               @NotBlank @Size(max = 500) String description,
                               @NotNull @Positive @Digits(integer = 8, fraction = 2) BigDecimal price,
                               @NotNull @Positive Integer duration,
                               @NotNull Set<Long> categoryIds) {}
