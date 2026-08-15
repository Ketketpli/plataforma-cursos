package com.example.cursos_backend.controller;

import com.example.cursos_backend.dtos.EnrollResponseDTO;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.services.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses/{courseId}/enroll")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollResponseDTO> enroll(@PathVariable Long courseId, @AuthenticationPrincipal User student) {

        EnrollResponseDTO newEnroll = enrollmentService.enroll(courseId, student);

        return ResponseEntity.status(HttpStatus.CREATED).body(newEnroll);
    }
}
