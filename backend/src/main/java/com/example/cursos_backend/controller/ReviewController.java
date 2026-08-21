package com.example.cursos_backend.controller;

import com.example.cursos_backend.dtos.ReviewRequestDTO;
import com.example.cursos_backend.dtos.ReviewResponseDTO;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/courses/{courseId}/reviews")
    public ResponseEntity<ReviewResponseDTO> createReview(@PathVariable Long courseId, @Valid @RequestBody ReviewRequestDTO request, @AuthenticationPrincipal User student) {

        ReviewResponseDTO response = reviewService.createReview(student.getId(), courseId, request);

        URI uri = URI.create("/reviews/" + response.id());

        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping("/courses/{courseId}/reviews")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long reviewId, @Valid @RequestBody ReviewRequestDTO request, @AuthenticationPrincipal User student) {

        return ResponseEntity.ok(reviewService.updateReview(request, student, reviewId));
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<Page<ReviewResponseDTO>> getAllReviews(@PathVariable Long courseId, Pageable pageable) {

        return ResponseEntity.ok(reviewService.getAllReviews(courseId, pageable));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal User student) {

        reviewService.deleteReview(reviewId, student);

        return ResponseEntity.noContent().build();
    }
}
