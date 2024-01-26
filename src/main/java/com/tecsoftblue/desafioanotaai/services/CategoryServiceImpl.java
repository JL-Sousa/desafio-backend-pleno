package com.tecsoftblue.desafioanotaai.services;

import com.tecsoftblue.desafioanotaai.domain.category.Category;
import com.tecsoftblue.desafioanotaai.domain.category.CategoryDTO;
import com.tecsoftblue.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import com.tecsoftblue.desafioanotaai.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService{

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryDTO insert(CategoryDTO categoryData) {
        Category category = new Category(categoryData);
        repository.save(category);
        return convertToDTO(category);
    }

    @Override
    public List<CategoryDTO> findAllCategories() {
        List<Category> categories = repository.findAll();
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    @Override
    public CategoryDTO update(String id, CategoryDTO categoryData) {
        Category category = repository.findById(id).orElseThrow(CategoryNotFoundException::new);
        if(!categoryData.title().isEmpty()) {
            category.setTitle(categoryData.title());
        }
        if(!categoryData.description().isEmpty()) {
            category.setDescription(categoryData.description());
        }
        repository.save(category);

        return convertToDTO(category);
    }

    @Override
    public void delete(String id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new CategoryNotFoundException("Categoria não encontrada com o ID: " + id);
        }
    }

    @Override
    public Optional<Category> getById(String id) {
        return this.repository.findById(id);
    }

    private CategoryDTO convertToDTO(Category cat) {
        return new CategoryDTO(cat.getTitle(), cat.getDescription(), cat.getOwnerId());
    }
}
