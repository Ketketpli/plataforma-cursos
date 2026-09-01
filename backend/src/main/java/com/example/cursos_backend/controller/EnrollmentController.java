package com.example.cursos_backend.controller;

import com.example.cursos_backend.dtos.EnrollResponseDTO;
import com.example.cursos_backend.dtos.StudentCourseDashboardDTO;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.services.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/courses/{courseId}/enroll")
    public ResponseEntity<EnrollResponseDTO> enroll(@PathVariable Long courseId, @AuthenticationPrincipal User student) {

        EnrollResponseDTO newEnroll = enrollmentService.enroll(courseId, student);

        return ResponseEntity.status(HttpStatus.CREATED).body(newEnroll);
    }

    @GetMapping("/students/me/courses")
    public ResponseEntity<List<StudentCourseDashboardDTO>> getMyCourses (@AuthenticationPrincipal User student) {

        List<StudentCourseDashboardDTO> enrollments = enrollmentService.getMyCourses(student);

        return ResponseEntity.ok(enrollments);
    }
}
