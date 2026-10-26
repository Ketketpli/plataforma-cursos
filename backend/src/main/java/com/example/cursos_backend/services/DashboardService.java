package com.example.cursos_backend.services;

import com.example.cursos_backend.dtos.InstructorCourseDashboardDTO;
import com.example.cursos_backend.dtos.StudentCourseDashboardDTO;
import com.example.cursos_backend.model.Course;
import com.example.cursos_backend.model.Enrollment;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.repositories.CourseRepository;
import com.example.cursos_backend.repositories.EnrollmentRepository;
import com.example.cursos_backend.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;
    private final LessonProgressService lessonProgressService;

    public List<InstructorCourseDashboardDTO> getInstructorCourses(User instructor, Pageable pageable) {

        List<Course> courses = courseRepository.findByInstructorAndActiveTrue(instructor);

        return courses.stream()
                .map(course -> new InstructorCourseDashboardDTO(
                        course.getId(),
                        course.getName(),
                        enrollmentRepository.countByCourseId(course.getId()),
                        Objects.requireNonNullElse(reviewRepository.findAverageRatingByCourseId(course.getId()), 0.0), // evita que um null seja recebido pelo front
                        reviewRepository.findByEnrollmentCourseId(course.getId(), pageable)
                                .map(reviewService::toResponse)))
                .toList();
    }

    public List<StudentCourseDashboardDTO> getStudentCourses(User student) {

        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(student.getId());

        return enrollments.stream()
                .map(enrollment -> new StudentCourseDashboardDTO(
                        enrollment.getCourse().getId(),
                        enrollment.getCourse().getName(),
                        enrollment.getCourse().getInstructor().getName(),
                        lessonProgressService.calculateProgress(enrollment.getId(), student)))
                .toList();
    }
}
