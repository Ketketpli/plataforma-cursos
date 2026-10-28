package com.example.cursos_backend.dtos;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LessonResponseDTO(Long id,
                                String title,
                                String description,
                                String content,
                                Integer lessonOrder) {}
