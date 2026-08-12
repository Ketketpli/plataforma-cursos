package com.example.cursos_backend.dtos;

public record LessonResponseDTO(Long id,
                                String title,
                                String description,
                                String content,
                                Integer lessonOrder) {}
