package com.example.cursos_backend.dtos;

import java.util.List;

public record InstructorCourseDashboardDTO(Long courseId,
                                           String courseName,
                                           Long enrolledStudentCount,
                                           Double averageRating,
                                           List<ReviewResponseDTO> reviews) {}
