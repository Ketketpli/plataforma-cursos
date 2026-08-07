package com.example.cursos_backend.services;

import com.example.cursos_backend.dtos.CourseRequestDTO;
import com.example.cursos_backend.dtos.CourseResponseDTO;
import com.example.cursos_backend.enums.Role;
import com.example.cursos_backend.exceptions.InvalidAccessException;
import com.example.cursos_backend.exceptions.ValueNotFoundException;
import com.example.cursos_backend.model.Category;
import com.example.cursos_backend.model.Course;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.repositories.CategoryRepository;
import com.example.cursos_backend.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;


    public CourseResponseDTO createCourse(CourseRequestDTO request, User instructor) {

        HashSet<Category> categories = new HashSet<>(categoryRepository.findAllById(request.categoryIds()));

        Course course = new Course();
        course.setName(request.name());
        course.setDescription(request.description());
        course.setPrice(request.price());
        course.setDuration(request.duration());
        course.setInstructor(instructor);
        course.setCategories(categories);
        Course saved = courseRepository.save(course);

        return toResponseDTO(saved);
    }

    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO request, User user) {

        Course course = courseRepository.findById(id).orElseThrow(() -> new ValueNotFoundException("Curso não encontrado"));

        checkAccess(course, user);

        HashSet<Category> categories = new HashSet<>(categoryRepository.findAllById(request.categoryIds()));

        course.setName(request.name());
        course.setDescription(request.description());
        course.setPrice(request.price());
        course.setDuration(request.duration());
        course.setCategories(categories);
        Course saved = courseRepository.save(course);

        return toResponseDTO(saved);
    }

    public void deleteCourse(Long id, User user) {

        Course course = courseRepository.findById(id).orElseThrow(() -> new ValueNotFoundException("Curso não encontrado"));

        checkAccess(course, user);

        courseRepository.deleteById(id);
    }

    public Page<CourseResponseDTO> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable).map(this::toResponseDTO);
    }

    private void checkAccess(Course course, User user) {

        boolean isOwner = course.getInstructor().getId().equals(user.getId());
        boolean isAdmin = user.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new InvalidAccessException();
        }
    }

    private CourseResponseDTO toResponseDTO(Course course) {

        return new CourseResponseDTO(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getPrice(),
                course.getDuration(),
                course.getInstructor().getName(),
                course.getCategories().stream().map(Category::getName).collect(Collectors.toSet()));
    }
}
