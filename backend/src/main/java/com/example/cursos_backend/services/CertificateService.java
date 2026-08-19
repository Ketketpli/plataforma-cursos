package com.example.cursos_backend.services;

import com.example.cursos_backend.dtos.CertificateResponseDTO;
import com.example.cursos_backend.exceptions.CourseNotCompleteException;
import com.example.cursos_backend.exceptions.ValueNotFoundException;
import com.example.cursos_backend.infra.AuthorizationHelper;
import com.example.cursos_backend.model.Certificate;
import com.example.cursos_backend.model.Enrollment;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.repositories.CertificateRepository;
import com.example.cursos_backend.repositories.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AuthorizationHelper authorizationHelper;
    private final LessonProgressService lessonProgressService;

    public CertificateResponseDTO issue(Long enrollmentId, User student) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ValueNotFoundException("Matrícula não encontrada"));

        authorizationHelper.checkOwner(enrollment.getStudent().getId(), student);

        double progress = lessonProgressService.calculateProgress(enrollmentId, student);

        if(progress < 100) {
            throw new CourseNotCompleteException();
        }

        Certificate certificate = new Certificate();
        certificate.setEnrollment(enrollment);
        certificate.setIssuedAt(LocalDateTime.now());
        certificateRepository.save(certificate);

        return new CertificateResponseDTO(
                certificate.getId(),
                certificate.getEnrollment().getId(),
                certificate.getIssuedAt());
    }
}
