package com.example.cursos_backend.repositories;

import com.example.cursos_backend.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    List<Certificate> findByEnrollmentStudentId(Long studentId);
}
