package com.example.cursos_backend.dtos;

public record StudentCourseDashBoardDTO(Long courseId,
                                        String courseName,
                                        String instructorName,
                                        Double progressPercentage) {}
