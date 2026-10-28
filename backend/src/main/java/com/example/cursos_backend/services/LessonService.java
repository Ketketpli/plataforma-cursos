package com.example.cursos_backend.services;

import com.example.cursos_backend.dtos.LessonRequestDTO;
import com.example.cursos_backend.dtos.LessonResponseDTO;
import com.example.cursos_backend.exceptions.ValueNotFoundException;
import com.example.cursos_backend.infra.AuthorizationHelper;
import com.example.cursos_backend.model.Course;
import com.example.cursos_backend.model.Lesson;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.repositories.CourseRepository;
import com.example.cursos_backend.repositories.EnrollmentRepository;
import com.example.cursos_backend.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final AuthorizationHelper authorizationHelper;
    private final EnrollmentRepository enrollmentRepository;

    public LessonResponseDTO createLesson(LessonRequestDTO request, Long courseId, User user) {

        Course course = courseRepository.findByIdAndActiveTrue(courseId)
                .orElseThrow(() -> new ValueNotFoundException("Curso não encontrado"));

        authorizationHelper.checkOwnerOrAdmin(course.getInstructor().getId(), user);

        Lesson lesson = new Lesson();
        lesson.setTitle(request.title());
        lesson.setDescription(request.description());
        lesson.setContent(request.content());
        lesson.setLessonOrder(request.lessonOrder());
        lesson.setCourse(course);

        Lesson saved = lessonRepository.save(lesson);

        return toResponseDTO(saved, true);
    }

    public Page<LessonResponseDTO> getLessonByCourse(Long courseId, User user, Pageable pageable) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ValueNotFoundException("Curso não encontrado"));

        boolean hasAccess = authorizationHelper.isOwnerOrAdmin(course.getInstructor().getId(), user)
                || enrollmentRepository.existsByStudentIdAndCourseId(user.getId(), courseId);

        if (!course.isActive() && !hasAccess) {
            throw new ValueNotFoundException("Curso não encontrado");
        }

        Page<Lesson> lessons = lessonRepository.findByCourseId(courseId, pageable);

        return lessons.map(lesson -> toResponseDTO(lesson, hasAccess));
    }

    public LessonResponseDTO updateLesson(Long lessonId, LessonRequestDTO request, User user) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ValueNotFoundException("Aula não encontrada"));

        authorizationHelper.checkOwnerOrAdmin(lesson.getCourse().getInstructor().getId(), user);

        lesson.setTitle(request.title());
        lesson.setDescription(request.description());
        lesson.setContent(request.content());
        lesson.setLessonOrder(request.lessonOrder());

        Lesson saved = lessonRepository.save(lesson);

        return toResponseDTO(saved, true);
    }

    public void deleteLesson(Long lessonId, User user) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ValueNotFoundException("Aula não encontrada"));

        authorizationHelper.checkOwnerOrAdmin(lesson.getCourse().getInstructor().getId(), user);

        lessonRepository.delete(lesson);
    }

    private LessonResponseDTO toResponseDTO(Lesson lesson, boolean hasAcess) {
        return new LessonResponseDTO(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getDescription(),
                hasAcess ? lesson.getContent() : null,
                lesson.getLessonOrder());
    }
}