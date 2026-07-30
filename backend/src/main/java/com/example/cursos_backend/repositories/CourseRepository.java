package com.example.cursos_backend.repositories;

import com.example.cursos_backend.model.Course;
import com.example.cursos_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByInstructor(User instructor);
}
