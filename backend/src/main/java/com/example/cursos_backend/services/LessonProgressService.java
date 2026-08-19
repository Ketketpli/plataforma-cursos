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

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonProgressService {

    private final LessonProgressRepository lessonProgressRepository;
    private final LessonRepository lessonRepository;
    private final AuthorizationHelper authorizationHelper;
    private final EnrollmentRepository enrollmentRepository;

    public void initializeProgress(Enrollment enrollment) {
        List<Lesson> lessons = lessonRepository.findAllByCourseId(enrollment.getCourse().getId());

        List<LessonProgress> progressList = lessons.stream()
                .map(lesson ->
                {LessonProgress progress = new LessonProgress();
                progress.setEnrollment(enrollment);
                progress.setLesson(lesson);
                progress.setCompleted(false);
                return progress;})
                .toList();

        lessonProgressRepository.saveAll(progressList);

        // O .stream() percorre a lista de Lesson
        // O .map(...) transforma cada Lesson num LessonProgress novo (com o enrollment fixo, a lesson daquela iteração, e completed = false)
        // O .toList() fecha coletando tudo numa lista nova. Depois é só saveAll nessa lista.
    }

    public void toggleCompletion(Long enrollmentId, Long lessonId, User student) {

        LessonProgress progress = lessonProgressRepository.findByEnrollmentIdAndLessonId(enrollmentId, lessonId)
                .orElseThrow(() -> new ValueNotFoundException("Progresso não encontrado"));

        authorizationHelper.checkOwner(progress.getEnrollment().getStudent().getId(), student);

        progress.setCompleted(!progress.isCompleted());

        lessonProgressRepository.save(progress);

        // busca o registro de progresso daquela matrícula+aula específica
        // checkOwner garante que só o aluno dono da matrícula pode marcar/desmarcar
        // !progress.isCompleted() inverte o valor atual: true vira false e false vira true
        //   -> mesmo método serve tanto pra marcar quanto pra desmarcar, sem precisar de if
        // save persiste a mudança no banco
    }

    public double calculateProgress(Long enrollmentId, User student) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ValueNotFoundException("Matrícula não encontrada"));

        Long courseId = enrollment.getCourse().getId();

        authorizationHelper.checkOwner(enrollment.getStudent().getId(), student);

        int lessons = lessonRepository.findAllByCourseId(courseId).size();
        long completed = lessonProgressRepository.countByEnrollmentIdAndCompleted(enrollmentId, true);

        return (double) completed / lessons * 100;
    }
}
