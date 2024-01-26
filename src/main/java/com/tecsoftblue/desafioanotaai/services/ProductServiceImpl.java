package com.tecsoftblue.desafioanotaai.services;

import com.tecsoftblue.desafioanotaai.domain.category.Category;
import com.tecsoftblue.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.tecsoftblue.desafioanotaai.domain.product.CreateProductResponse;
import com.tecsoftblue.desafioanotaai.domain.product.Product;
import com.tecsoftblue.desafioanotaai.domain.product.ProductDTO;
import com.tecsoftblue.desafioanotaai.domain.product.exceptions.ProductNotFoundException;
import com.tecsoftblue.desafioanotaai.repositories.ProductRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService{

    private final ICategoryService categoryService;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ICategoryService categoryService, ProductRepository productRepository) {
        this.categoryService = categoryService;
        this.productRepository = productRepository;
    }


    @Override
    public CreateProductResponse insert(ProductDTO productData) {
        Category category = categoryService.getById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        Product product = new Product(productData);
        product.setCategory(category);
        productRepository.save(product);
        return convertToProductResponse(product);
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO update(String id, ProductDTO productData) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        if(productData.categoryId() != null) {
            categoryService.getById(productData.categoryId())
                    .ifPresent(product::setCategory);
        }

        if(!productData.title().isEmpty()) {
            product.setTitle(productData.title());
        }
        if(!productData.description().isEmpty()) {
            product.setDescription(productData.description());
        }
        if(!(productData.price() == null)) {
            product.setPrice(productData.price());
        }

        productRepository.save(product);
        return convertToDTO(product);

    }

    @Override
    public void delete(String id) {

        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new CategoryNotFoundException("Product não encontrado com o ID: " + id);
        }
    }

    private ProductDTO convertToDTO(Product prod) {
        return new ProductDTO(
                prod.getTitle(),
                prod.getDescription(),
                prod.getOwnerId(),
                prod.getPrice(),
                prod.getCategory().getId()
        );
    }

        private CreateProductResponse convertToProductResponse(Product prod) {
            return new CreateProductResponse(
                    prod.getId(),
                    prod.getTitle(),
                    prod.getDescription(),
                    prod.getOwnerId(),
                    prod.getPrice(),
                    prod.getCategory().getId()
            );
    }
}
