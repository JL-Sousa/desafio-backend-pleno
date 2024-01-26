package com.tecsoftblue.desafioanotaai.services;

import com.tecsoftblue.desafioanotaai.domain.product.CreateProductResponse;
import com.tecsoftblue.desafioanotaai.domain.product.ProductDTO;

import java.util.List;

public interface IProductService {

    CreateProductResponse insert(ProductDTO productData);

    List<ProductDTO> findAll();

    ProductDTO update(String id, ProductDTO productData);

    void delete(String id);
}
