package org.app.app.service;

import org.app.app.dto.ProductRequest;
import org.app.app.dto.ProductResponse;
import org.app.app.exception.NotFoundException;
import org.app.app.mapper.ProductMapper;
import org.app.app.model.Product;
import org.app.app.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private static final Logger log =
            LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    // GET All Products
    public List<ProductResponse> getProducts(){
        log.info("Fetching all products");

        List<ProductResponse> response = productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());

        log.info("Fetched {} products", response.size());
        return response;
    }

    // POST Inserting new Product
    public ProductResponse insertProduct(ProductRequest request) {
        log.info("Creating product with name {}", request.getName());

        Product product = ProductMapper.toEntity(request);
        Product saved = productRepository.save(product);

        log.info("Product {} created with id {}", saved.getName(), saved.getId());
        return ProductMapper.toResponse(saved);
    }

    // GET Find Product by Id
    public ProductResponse findProductById(Long id) {
        log.info("Fetching product with id {}", id);
        return productRepository.findById(id)
                .map(ProductMapper::toResponse)
                .orElseThrow(() -> {
                    log.warn("Product with id {} not found", id);
                    return new NotFoundException("Product not found with id " + id);
                });
    }

    // PUT Update Products Information
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        log.info("Updating product with id {}", id);

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product with id {} not found", id);
                    return new RuntimeException("Product not found");
                });

        existing.setName(request.getName());
        existing.setPrice(request.getPrice());
        existing.setDescription(request.getDescription());
        Product updated = productRepository.save(existing);

        log.info("Updated product with id {}", id);
        return ProductMapper.toResponse(updated);
    }

    // DELETE Selected Product
    public void deleteProduct(Long id) {
        log.info("Deleting product with id {}", id);

        if(!productRepository.existsById(id)){
            log.warn("Cannot delete product {} because it does not exist", id);
            throw new NotFoundException("Product not found with id " + id);
        }

        log.info("Product with id {} deleted successfully", id);

        productRepository.deleteById(id);
    }
}
