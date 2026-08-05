package com.example.cursos_backend.services;

import com.example.cursos_backend.dtos.CategoryRequestDTO;
import com.example.cursos_backend.dtos.CategoryResponseDTO;
import com.example.cursos_backend.exceptions.CategoryAlreadyExistsException;
import com.example.cursos_backend.model.Category;
import com.example.cursos_backend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponseDTO createCategory(CategoryRequestDTO request) {

        if(categoryRepository.existsByName(request.name())) {
            throw new CategoryAlreadyExistsException();
        }

        Category category = new Category();
        category.setName(request.name());

        Category newCategory = categoryRepository.save(category);
        return new CategoryResponseDTO(
                newCategory.getId(),
                newCategory.getName()
        );
    }
}
