package org.app.app.controller;

import jakarta.validation.Valid;
import org.app.app.dto.ProductRequest;
import org.app.app.dto.ProductResponse;
import org.app.app.response.ApiResponse;
import org.app.app.service.ProductService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getProducts(
            @PageableDefault(size = 10, sort = "name")
            @ParameterObject Pageable pageable){
        Page<ProductResponse> response = productService.getProducts(pageable);

        ApiResponse<Page<ProductResponse>>  apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Products fetched successfully",
                response
        );
        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> addProduct(@Valid @RequestBody ProductRequest request){
        ProductResponse response = productService.insertProduct(request);

        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Product inserted successfully",
                response
        );
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(apiResponse);
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> addManyProducts(@RequestBody List<ProductRequest> request){
        List<ProductResponse> response = productService.insertManyProducts(request);

        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Products inserted successfully",
                response
        );
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> findProductById(@PathVariable Long id){
        ProductResponse response = productService.findProductById(id);

        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Product by id='"+id+"' found",
                response
        );
        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> update(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
        ProductResponse response = productService.updateProduct(id, request);

        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Product by id='"+id+"' successfully changed",
                response
        );
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){
        productService.deleteProduct(id);

        ApiResponse<Void> response =
                new ApiResponse<>(
                        HttpStatus.NO_CONTENT.value(),
                        "Product deleted successfully",
                        null
                );

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).body(response);
    }
}
