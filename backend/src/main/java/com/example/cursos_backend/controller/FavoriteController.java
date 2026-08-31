package com.example.cursos_backend.controller;

import com.example.cursos_backend.dtos.CourseResponseDTO;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.services.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/courses/{courseId}/favorites")
    public ResponseEntity<Boolean> toggleFavorite(@PathVariable Long courseId, @AuthenticationPrincipal User student) {

        Boolean favorite = favoriteService.toggleFavorite(courseId, student);

        return ResponseEntity.ok(favorite);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<CourseResponseDTO>> getFavorites(@AuthenticationPrincipal User student) {

        List<CourseResponseDTO> favorites = favoriteService.getFavorites(student.getId());

        return ResponseEntity.ok(favorites);
    }
}
