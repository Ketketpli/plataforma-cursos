package com.example.cursos_backend.repositories;

import com.example.cursos_backend.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByEnrollmentCourseId(Long courseId, Pageable pageable);
}
