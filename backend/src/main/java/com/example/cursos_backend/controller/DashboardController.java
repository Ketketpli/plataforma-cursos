package com.example.cursos_backend.controller;

import com.example.cursos_backend.dtos.InstructorCourseDashboardDTO;
import com.example.cursos_backend.dtos.StudentCourseDashboardDTO;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.services.DashboardService;
import com.example.cursos_backend.services.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DashboardController{

    private final DashboardService dashboardService;
    private final EnrollmentService enrollmentService;

    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    @GetMapping("/instructors/me/courses")
    public ResponseEntity<List<InstructorCourseDashboardDTO>> getInstructorDashboard(@AuthenticationPrincipal User instructor, Pageable pageable){
        return ResponseEntity.ok(dashboardService.getInstructorCourses(instructor, pageable));
    }

    @GetMapping("/students/me/courses")
    public ResponseEntity<List<StudentCourseDashboardDTO>> getStudentCourses (@AuthenticationPrincipal User student) {

        return ResponseEntity.ok(dashboardService.getStudentCourses(student));
    }
}
