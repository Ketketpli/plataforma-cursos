package com.example.cursos_backend.repositories;

import com.example.cursos_backend.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByTitle(String lessonTitle);

    List<Lesson> findByCourseId(Long courseId);
}
