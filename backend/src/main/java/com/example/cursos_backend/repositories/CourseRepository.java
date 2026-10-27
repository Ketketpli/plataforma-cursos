package com.example.cursos_backend.repositories;

import com.example.cursos_backend.model.Course;
import com.example.cursos_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByActiveTrue(Pageable pageable);

    List<Course> findByInstructorAndActiveTrue(User instructor);

    Optional<Course> findByIdAndActiveTrue(Long id);
}
