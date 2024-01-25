package com.tecsoftblue.desafioanotaai.services;

import com.tecsoftblue.desafioanotaai.domain.category.Category;
import com.tecsoftblue.desafioanotaai.domain.category.CategoryDTO;
import com.tecsoftblue.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.tecsoftblue.desafioanotaai.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.function.Executable;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void testInsertCategory() {

        CategoryDTO categoryDTO = new CategoryDTO(
                "Categoria de Teste",
                "Descrição da Categoria de Teste",
                "123");


        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(new Category(categoryDTO));

        CategoryDTO savedCategory = categoryService.insert(categoryDTO);

        Assertions.assertNotNull(savedCategory);

    }

    @Test
    public void testFindAllCategories() {

        List<Category> mockCategories = Arrays.asList(
                new Category("1", "Categoria 1", "Descrição 1", "123"),
                new Category("2", "Categoria 2", "Descrição 2", "456")
        );
        Mockito.when(categoryRepository.findAll()).thenReturn(mockCategories);

        List<CategoryDTO> resultCategories = categoryService.findAllCategories();

        assert resultCategories != null;
        assert resultCategories.size() == 2;
    }

    @Test
    public void testDeleteCategory() {
        String categoryIdToDelete = "1";

        Mockito.doThrow(EmptyResultDataAccessException.class).when(categoryRepository).deleteById(categoryIdToDelete);

        Executable executable = () -> categoryService.delete(categoryIdToDelete);
        assertThrows(CategoryNotFoundException.class, executable);
    }
}
