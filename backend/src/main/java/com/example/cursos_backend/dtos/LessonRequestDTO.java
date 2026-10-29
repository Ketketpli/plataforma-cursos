package com.example.cursos_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record LessonRequestDTO(@NotBlank @Size(max = 100) String title,
                               @NotBlank @Size(max = 500) String description,
                               @Size(max = 500) String content,
                               @NotNull @Positive Integer lessonOrder) {}
