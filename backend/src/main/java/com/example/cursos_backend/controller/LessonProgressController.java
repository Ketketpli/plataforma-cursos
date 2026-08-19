package com.example.cursos_backend.controller;

import com.example.cursos_backend.model.User;
import com.example.cursos_backend.services.LessonProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollments/{enrollmentId}")
@RequiredArgsConstructor
public class LessonProgressController {

    private final LessonProgressService lessonProgressService;

    @PatchMapping("/lessons/{lessonId}/progress")
    public ResponseEntity<Void> toggle(@PathVariable Long lessonId, @PathVariable Long enrollmentId, @AuthenticationPrincipal User student) {

        lessonProgressService.toggleCompletion(enrollmentId, lessonId, student);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/progress")
    public ResponseEntity<Double> percentage(@PathVariable Long enrollmentId, @AuthenticationPrincipal User student) {

        Double result = lessonProgressService.calculateProgress(enrollmentId, student);

        return ResponseEntity.ok(result);
    }
}
