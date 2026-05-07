package org.app.app.repository;

import org.app.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByPriceLessThan(double price);
    List<Product> findByPriceBetween(double min, double max);
    boolean existsByNameIgnoreCase(String name);
}
