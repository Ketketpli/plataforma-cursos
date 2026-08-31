package com.example.cursos_backend.controller;

import com.example.cursos_backend.dtos.CertificateResponseDTO;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.services.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @PostMapping("/enrollments/{enrollmentId}/certificates")
    public ResponseEntity<CertificateResponseDTO> createCertificate(@PathVariable Long enrollmentId, @AuthenticationPrincipal User student) {

        CertificateResponseDTO newCertificate = certificateService.issue(enrollmentId, student);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCertificate);
    }

    @GetMapping("/certificates")
    public ResponseEntity<List<CertificateResponseDTO>> getMyCertificates(@AuthenticationPrincipal User student) {

        List<CertificateResponseDTO> certificates = certificateService.listByStudent(student.getId());
        return ResponseEntity.ok(certificates);
    }
}
