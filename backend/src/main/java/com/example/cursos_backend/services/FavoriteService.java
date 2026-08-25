package com.example.cursos_backend.services;

import com.example.cursos_backend.dtos.CourseResponseDTO;
import com.example.cursos_backend.exceptions.ValueNotFoundException;
import com.example.cursos_backend.model.Course;
import com.example.cursos_backend.model.Favorite;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.repositories.CourseRepository;
import com.example.cursos_backend.repositories.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;

    @Transactional
    public Boolean toggleFavorite(User student, Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ValueNotFoundException("Curso não encontrado"));

        if (favoriteRepository.existsByStudentIdAndCourseId(student.getId(), courseId)) {
            favoriteRepository.deleteByStudentIdAndCourseId(student.getId(), courseId);

            return false; // remover favorito
        }

        Favorite favorite = new Favorite();
        favorite.setStudent(student);
        favorite.setCourse(course);
        favoriteRepository.save(favorite);
        return true; // favoritar
    }

    public List<CourseResponseDTO> getFavorites(Long studentId) {
        List<Favorite> favorites = favoriteRepository.findByStudentId(studentId);
        return favorites.stream()
                .map(favorite -> courseService.toResponseDTO(favorite.getCourse()))
                .toList();
    }
}
