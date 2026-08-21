package com.example.cursos_backend.services;

import com.example.cursos_backend.dtos.ReviewRequestDTO;
import com.example.cursos_backend.dtos.ReviewResponseDTO;
import com.example.cursos_backend.exceptions.ValueNotFoundException;
import com.example.cursos_backend.infra.AuthorizationHelper;
import com.example.cursos_backend.model.Enrollment;
import com.example.cursos_backend.model.Review;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.repositories.EnrollmentRepository;
import com.example.cursos_backend.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AuthorizationHelper authorizationHelper;

    public ReviewResponseDTO createReview(Long studentId, Long courseId, ReviewRequestDTO request) {

        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new ValueNotFoundException("Matricula não encontrada"));

        Review review = new Review();
        review.setEnrollment(enrollment);
        review.setComment(request.comment());
        review.setRating(request.rating());

        reviewRepository.save(review);

        return toResponse(review);
    }

    public ReviewResponseDTO updateReview(ReviewRequestDTO request, User student, Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ValueNotFoundException("Review não encontrada"));

        authorizationHelper.checkOwner(review.getEnrollment().getStudent().getId(), student);

        review.setComment(request.comment());
        review.setRating(request.rating());

        reviewRepository.save(review);

        return toResponse(review);
    }

    public Page<ReviewResponseDTO> getAllReviews(Long courseId, Pageable pageable) {

        Page<Review> reviews = reviewRepository.findByEnrollmentCourseId(courseId, pageable);

        return reviews.map(this::toResponse);
    }

    private ReviewResponseDTO toResponse(Review review) {
        return new ReviewResponseDTO(
                review.getId(),
                review.getEnrollment().getStudent().getName(),
                review.getRating(),
                review.getComment()
        );
    }
}
