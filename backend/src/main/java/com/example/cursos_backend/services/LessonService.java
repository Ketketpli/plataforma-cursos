package com.example.cursos_backend.services;

import com.example.cursos_backend.dtos.LessonRequestDTO;
import com.example.cursos_backend.dtos.LessonResponseDTO;
import com.example.cursos_backend.exceptions.ValueNotFoundException;
import com.example.cursos_backend.infra.AuthorizationHelper;
import com.example.cursos_backend.model.Course;
import com.example.cursos_backend.model.Lesson;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.repositories.CourseRepository;
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

    public LessonResponseDTO createLesson(LessonRequestDTO request, Long courseId, User user) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ValueNotFoundException("Curso não encontrado"));

        authorizationHelper.checkOwnerOrAdmin(course.getInstructor().getId(), user);

        Lesson lesson = new Lesson();
        lesson.setTitle(request.title());
        lesson.setDescription(request.description());
        lesson.setContent(request.content());
        lesson.setLessonOrder(request.lessonOrder());
        lesson.setCourse(course);

        Lesson saved = lessonRepository.save(lesson);

        return toResponseDTO(saved);
    }

    public Page<LessonResponseDTO> getLessonByCourse(Long courseId, Pageable pageable) {

        Page<Lesson> lessons = lessonRepository.findByCourseId(courseId, pageable);

        return lessons.map(this::toResponseDTO);
    }

    public void deleteLesson(Long lessonId, User user) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ValueNotFoundException("Aula não encontrada"));

        authorizationHelper.checkOwnerOrAdmin(lesson.getCourse().getInstructor().getId(), user);

        lessonRepository.delete(lesson);
    }

    private LessonResponseDTO toResponseDTO(Lesson lesson) {
        return new LessonResponseDTO(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getDescription(),
                lesson.getContent(),
                lesson.getLessonOrder());
    }
}