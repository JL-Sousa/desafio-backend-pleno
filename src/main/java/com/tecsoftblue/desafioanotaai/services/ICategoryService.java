package com.tecsoftblue.desafioanotaai.services;

import com.tecsoftblue.desafioanotaai.domain.category.CategoryDTO;

import java.util.List;

public interface ICategoryService {

    CategoryDTO insert(CategoryDTO categoryData);

    List<CategoryDTO> findAllCategories();

    CategoryDTO update(String id, CategoryDTO categoryData);

    void delete(String id);
}
