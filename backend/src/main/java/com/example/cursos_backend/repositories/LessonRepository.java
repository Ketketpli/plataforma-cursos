package com.example.cursos_backend.repositories;

import com.example.cursos_backend.model.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByTitle(String lessonTitle);

    Page<Lesson> findByCourseId(Long courseId, Pageable pageable); // Lista paginada

    List<Lesson> findAllByCourseId(Long courseId); // Lista completa
}
