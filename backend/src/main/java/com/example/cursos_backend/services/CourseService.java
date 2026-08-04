package com.example.cursos_backend.services;

import com.example.cursos_backend.dtos.CourseRequestDTO;
import com.example.cursos_backend.dtos.CourseResponseDTO;
import com.example.cursos_backend.model.Category;
import com.example.cursos_backend.model.Course;
import com.example.cursos_backend.model.User;
import com.example.cursos_backend.repositories.CategoryRepository;
import com.example.cursos_backend.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;


    public CourseResponseDTO createCourse(CourseRequestDTO request, User instructor) {
        List<Category> list = categoryRepository.findAllById(request.categoryIds());
        HashSet<Category> categories = new HashSet<>(list);
        Course course = new Course();
        course.setName(request.name());
        course.setDescription(request.description());
        course.setPrice(request.price());
        course.setDuration(request.duration());
        course.setInstructor(instructor);
        course.setCategories(categories);
        Course newCourse = courseRepository.save(course);

        Set<String> categoryNames = categories.stream()
                .map(Category::getName) // convertendo Stream<Category> para Stream<String>
                .collect(Collectors.toSet()); // juntando o resultado numa coleção de Set<String>

        return new CourseResponseDTO(
                newCourse.getId(),
                newCourse.getName(),
                newCourse.getDescription(),
                newCourse.getPrice(),
                newCourse.getDuration(),
                newCourse.getInstructor().getName(),
                categoryNames);
    }
}
