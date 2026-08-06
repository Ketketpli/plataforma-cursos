package com.example.cursos_backend.controller;

import com.example.cursos_backend.dtos.CourseRequestDTO;
import com.example.cursos_backend.dtos.CourseResponseDTO;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.services.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody CourseRequestDTO request, @AuthenticationPrincipal User user) {
        CourseResponseDTO newCourse = courseService.createCourse(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCourse);
    }
}
