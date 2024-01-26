package com.tecsoftblue.desafioanotaai.services;

import com.tecsoftblue.desafioanotaai.domain.category.Category;
import com.tecsoftblue.desafioanotaai.domain.category.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    CategoryDTO insert(CategoryDTO categoryData);

    List<CategoryDTO> findAllCategories();

    CategoryDTO update(String id, CategoryDTO categoryData);

    void delete(String id);

    Optional<Category> getById(String id);
}
