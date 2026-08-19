package com.example.cursos_backend.services;

import com.example.cursos_backend.dtos.EnrollResponseDTO;
import com.example.cursos_backend.exceptions.EnrollmentAlreadyExistException;
import com.example.cursos_backend.exceptions.ValueNotFoundException;
import com.example.cursos_backend.infra.AuthorizationHelper;
import com.example.cursos_backend.model.Course;
import com.example.cursos_backend.model.Enrollment;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.repositories.CourseRepository;
import com.example.cursos_backend.repositories.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AuthorizationHelper authorizationHelper;
    private final LessonProgressService lessonProgressService;

    public EnrollResponseDTO enroll(Long courseId, User student) {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ValueNotFoundException("Curso não encontrado"));
        authorizationHelper.checkNotOwner(course.getInstructor().getId(), student);
        if (enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), courseId)) {
            throw new EnrollmentAlreadyExistException("Aluno já matriculado");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setEnrolledAt(LocalDateTime.now());
        enrollmentRepository.save(enrollment);

        lessonProgressService.initializeProgress(enrollment);

        return new EnrollResponseDTO(enrollment.getId(),
                enrollment.getCourse().getId(),
                enrollment.getStudent().getId(),
                enrollment.getEnrolledAt());
    }
}
