package com.example.cursos_backend.controller;

import com.example.cursos_backend.dtos.LessonRequestDTO;
import com.example.cursos_backend.dtos.LessonResponseDTO;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.services.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/courses/{courseId}/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    @PostMapping
    public ResponseEntity<LessonResponseDTO> createLesson(@PathVariable Long courseId, @Valid @RequestBody LessonRequestDTO request, @AuthenticationPrincipal User user) {

        LessonResponseDTO newLesson = lessonService.createLesson(request, courseId, user);

        URI location = URI.create("/courses/" + courseId + "/lessons/" + newLesson.id());

        return ResponseEntity.created(location).body(newLesson);
    }

    @GetMapping
    public ResponseEntity<Page<LessonResponseDTO>> getLessonByCourse(@PathVariable Long courseId, Pageable pageable) {

        Page<LessonResponseDTO> lessons = lessonService.getLessonByCourse(courseId, pageable);

        return ResponseEntity.ok(lessons);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    @PutMapping("/{lessonId}")
    public ResponseEntity<LessonResponseDTO> updateLesson(@PathVariable Long lessonId, @Valid @RequestBody LessonRequestDTO request, @AuthenticationPrincipal User user) {

        LessonResponseDTO updatedLesson = lessonService.updateLesson(lessonId, request, user);

        return ResponseEntity.ok(updatedLesson);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long lessonId, @AuthenticationPrincipal User user) {

        lessonService.deleteLesson(lessonId, user);

        return ResponseEntity.noContent().build();
    }
}
