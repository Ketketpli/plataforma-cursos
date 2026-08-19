package com.example.cursos_backend.dtos;

import java.time.LocalDateTime;

public record CertificateResponseDTO(Long id,
                                     Long enrollmentId,
                                     LocalDateTime issuedAt) {}
