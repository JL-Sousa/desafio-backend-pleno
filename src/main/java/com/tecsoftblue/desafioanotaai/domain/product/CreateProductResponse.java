package com.tecsoftblue.desafioanotaai.domain.product;

public record CreateProductResponse(
        String id,
        String title,
        String description,
        String ownerId,
        Integer price,
        String categoryId
) {
}
