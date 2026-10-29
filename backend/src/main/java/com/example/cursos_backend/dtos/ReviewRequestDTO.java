package com.example.cursos_backend.dtos;

import jakarta.validation.constraints.*;

public record ReviewRequestDTO(@NotNull @Min(1) @Max(5) Integer rating,
                               @NotBlank @Size (max = 1000)String comment) {}
