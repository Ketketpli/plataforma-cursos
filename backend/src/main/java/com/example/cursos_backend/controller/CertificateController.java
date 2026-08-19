package com.example.cursos_backend.controller;

import com.example.cursos_backend.dtos.CertificateResponseDTO;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.services.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enrollments/{enrollmentId}/certificate")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @PostMapping
    public ResponseEntity<CertificateResponseDTO> createCertificate(@PathVariable Long enrollmentId, @AuthenticationPrincipal User student) {

        CertificateResponseDTO newCertificate = certificateService.issue(enrollmentId, student);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCertificate);
    }
}
