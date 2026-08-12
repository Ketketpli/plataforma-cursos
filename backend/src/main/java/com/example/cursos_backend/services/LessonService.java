package com.example.cursos_backend.services;

import com.example.cursos_backend.dtos.LessonRequestDTO;
import com.example.cursos_backend.dtos.LessonResponseDTO;
import com.example.cursos_backend.exceptions.ValueNotFoundException;
import com.example.cursos_backend.model.Course;
import com.example.cursos_backend.model.Lesson;
import com.example.cursos_backend.repositories.CourseRepository;
import com.example.cursos_backend.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonResponseDTO createLesson(LessonRequestDTO request, Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ValueNotFoundException("Curso não encontrado"));

        Lesson lesson = new Lesson();
        lesson.setTitle(request.title());
        lesson.setDescription(request.description());
        lesson.setContent(request.content());
        lesson.setLessonOrder(request.lessonOrder());
        lesson.setCourse(course);

        Lesson saved = lessonRepository.save(lesson);

        return toResponseDTO(saved);
    }

    public List<LessonResponseDTO> getLessonByCourse(Long courseId) {

        List<Lesson> lessons = lessonRepository.findByCourseId(courseId);

        return lessons.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public void deleteLesson (Long lessonId) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ValueNotFoundException("Aula não encontrada"));

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
