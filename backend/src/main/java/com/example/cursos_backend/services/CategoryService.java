package com.example.cursos_backend.services;

import com.example.cursos_backend.dtos.CategoryRequestDTO;
import com.example.cursos_backend.dtos.CategoryResponseDTO;
import com.example.cursos_backend.exceptions.CategoryAlreadyExistsException;
import com.example.cursos_backend.exceptions.ValueNotFoundException;
import com.example.cursos_backend.model.Category;
import com.example.cursos_backend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<CategoryResponseDTO> getAllCategories(Pageable pageable) {

        return categoryRepository.findAll(pageable)
                .map(category -> new CategoryResponseDTO(
                        category.getId(),
                        category.getName()));
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(ValueNotFoundException::new);

        category.setName(request.name());

        category = categoryRepository.save(category);

        return new CategoryResponseDTO(category.getId(), category.getName());
    }

    public void deleteCategory(Long id) {

        if (!categoryRepository.existsById(id)) {
            throw new ValueNotFoundException();
        }

        categoryRepository.deleteById(id);
    }
}
