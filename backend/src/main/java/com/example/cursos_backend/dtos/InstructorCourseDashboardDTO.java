package com.example.cursos_backend.dtos;

import org.springframework.data.domain.Page;

public record InstructorCourseDashboardDTO(Long courseId,
                                           String courseName,
                                           Long enrolledStudentCount,
                                           Double averageRating,
                                           Page<ReviewResponseDTO> reviews) {}
