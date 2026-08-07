package com.example.cursos_backend.controller;

import com.example.cursos_backend.dtos.CourseRequestDTO;
import com.example.cursos_backend.dtos.CourseResponseDTO;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.services.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody CourseRequestDTO request, @AuthenticationPrincipal User user) {

        CourseResponseDTO course = courseService.createCourse(request, user);

        URI location = URI.create("/course/" + course.id());

        return ResponseEntity.created(location).body(course);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequestDTO request, @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(courseService.updateCourse(id, request, user));
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id, @AuthenticationPrincipal User user) {

        courseService.deleteCourse(id, user);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponseDTO>> getAllCourses(Pageable pageable) {

        return ResponseEntity.ok(courseService.getAllCourses(pageable));
    }
}
