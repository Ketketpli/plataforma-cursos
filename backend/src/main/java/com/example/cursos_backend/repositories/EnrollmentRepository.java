package com.example.cursos_backend.repositories;

import com.example.cursos_backend.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    List<Enrollment> findByStudentId(Long studentId);

    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);
}
