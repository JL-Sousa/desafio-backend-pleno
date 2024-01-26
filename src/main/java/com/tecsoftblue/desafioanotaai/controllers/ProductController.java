package com.tecsoftblue.desafioanotaai.controllers;

import com.tecsoftblue.desafioanotaai.domain.product.CreateProductResponse;
import com.tecsoftblue.desafioanotaai.domain.product.ProductDTO;
import com.tecsoftblue.desafioanotaai.services.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final IProductService service;

    public ProductController(IProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> insert(@RequestBody ProductDTO productData, UriComponentsBuilder uriBuilder){
        CreateProductResponse productDTO = service.insert(productData);
        URI uri = uriBuilder
                .path("/api/v1/products/{id}")
                .buildAndExpand(productDTO.id())
                .toUri();
        return ResponseEntity.created(uri).body(productDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable String id, @RequestBody ProductDTO productData) {
        ProductDTO updatedProduct = service.update(id, productData);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
