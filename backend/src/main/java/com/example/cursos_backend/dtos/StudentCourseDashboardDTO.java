package com.example.cursos_backend.dtos;

public record StudentCourseDashboardDTO(Long courseId,
                                        String courseName,
                                        String instructorName,
                                        Double progressPercentage) {}
