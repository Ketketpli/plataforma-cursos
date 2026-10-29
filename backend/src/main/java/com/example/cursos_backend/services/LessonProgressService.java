package com.example.cursos_backend.services;

import com.example.cursos_backend.exceptions.ValueNotFoundException;
import com.example.cursos_backend.infra.AuthorizationHelper;
import com.example.cursos_backend.model.Enrollment;
import com.example.cursos_backend.model.Lesson;
import com.example.cursos_backend.model.LessonProgress;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.repositories.EnrollmentRepository;
import com.example.cursos_backend.repositories.LessonProgressRepository;
import com.example.cursos_backend.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonProgressService {

    private final LessonProgressRepository lessonProgressRepository;
    private final LessonRepository lessonRepository;
    private final AuthorizationHelper authorizationHelper;
    private final EnrollmentRepository enrollmentRepository;

    public void toggleCompletion(Long enrollmentId, Long lessonId, User student) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ValueNotFoundException("Matrícula não encontrada"));

        authorizationHelper.checkOwner(enrollment.getStudent().getId(), student);

        LessonProgress progress = lessonProgressRepository.findByEnrollmentIdAndLessonId(enrollmentId, lessonId)
                        .orElseGet(() -> createProgress(enrollment, lessonId));

        progress.setCompleted(!progress.isCompleted());

        lessonProgressRepository.save(progress);
    }

    private LessonProgress createProgress(Enrollment enrollment, Long lessonId) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ValueNotFoundException("Aula não encontrada"));

        if (!lesson.getCourse().getId().equals(enrollment.getCourse().getId())) {
            throw new ValueNotFoundException("Aula não encontrada");
        }

        LessonProgress progress = new LessonProgress();
        progress.setEnrollment(enrollment);
        progress.setLesson(lesson);
        progress.setCompleted(false);

        return progress;
    }

    public double calculateProgress(Long enrollmentId, User student) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ValueNotFoundException("Matrícula não encontrada"));

        Long courseId = enrollment.getCourse().getId();

        authorizationHelper.checkOwner(enrollment.getStudent().getId(), student);

        long lessons = lessonRepository.countByCourseId(courseId);

        if(lessons == 0) {
            return 0.0;
        }

        long completed = lessonProgressRepository.countByEnrollmentIdAndCompleted(enrollmentId, true);

        return (double) completed / lessons * 100;
    }
}
