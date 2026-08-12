package com.example.cursos_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LessonRequestDTO(@NotBlank String title,
                               @NotBlank String description,
                               String content,
                               @NotNull @Positive Integer lessonOrder) {}
