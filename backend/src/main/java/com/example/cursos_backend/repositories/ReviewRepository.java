package com.example.cursos_backend.repositories;

import com.example.cursos_backend.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByEnrollmentCourseId(Long courseId, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.enrollment.course.id = :courseId")
    Double findAverageRatingByCourseId(@Param("courseId") Long courseId);

    long countByCourseId(Long courseId);
}
