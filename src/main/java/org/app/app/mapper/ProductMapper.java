package org.app.app.mapper;

import org.app.app.dto.ProductRequest;
import org.app.app.dto.ProductResponse;
import org.app.app.model.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequest req) {
        Product p = new Product();
        p.setName(req.getName());
        p.setPrice(req.getPrice());
        p.setDescription(req.getDescription());
        return p;
    }

    public static ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getDescription()
        );
    }
}