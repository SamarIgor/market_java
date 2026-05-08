package org.app.app.controller;

import jakarta.validation.Valid;
import org.app.app.dto.*;
import org.app.app.model.InventoryItem;
import org.app.app.response.ApiResponse;
import org.app.app.service.InventoryService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/markets")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // ✅ GET
    @GetMapping("/{marketId}/inventory")
    public ResponseEntity<ApiResponse<Page<InventoryItemResponse>>> getInventory(
            @PathVariable Long marketId,
            @PageableDefault(size = 10, sort = "id")
            @ParameterObject Pageable pageable) {
        Page<InventoryItemResponse> inventoryItemResponses = inventoryService.getInventory(pageable, marketId);
        ApiResponse<Page<InventoryItemResponse>> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Inventory fetched successfully",
                inventoryItemResponses
        );
        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @GetMapping("/{marketId}/inventory/{productId}")
    public ResponseEntity<ApiResponse<InventoryItemResponse>> getInventoryItem(
            @PathVariable Long marketId,
            @PathVariable Long productId) {

        InventoryItemResponse response =
                inventoryService.getInventoryItem(
                        marketId,
                        productId
                );

        ApiResponse<InventoryItemResponse> apiResponse =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Inventory item fetched successfully",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    // ✅ POST
    @PostMapping("/{marketId}/inventory")
    public ResponseEntity<ApiResponse<InventoryItemResponse>>
    addProduct(@PathVariable Long marketId,
               @RequestBody AddProductToInventoryRequest request) {

        InventoryItemResponse inventoryItemResponse = inventoryService.addProduct(
                marketId,
                request.getProductId(),
                request.getQuantity()
        );

        ApiResponse<InventoryItemResponse> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Inventory item inserted successfully",
                inventoryItemResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(apiResponse);
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<InventoryItemResponse>>> createInventoryItems(
            @RequestBody List<InventoryItemRequest> requests
    ) {

        List<InventoryItemResponse> response =
                inventoryService.createInventoryItems(requests);

        ApiResponse<List<InventoryItemResponse>> apiResponse =
                new ApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "Inventory items created successfully",
                        response
                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    // ✅ PUT
    @PutMapping("/{marketId}/inventory/{productId}")
    public ResponseEntity<ApiResponse<InventoryItemResponse>> updateQuantity(
            @PathVariable Long marketId,
            @PathVariable Long productId,
            @RequestBody @Valid InventoryQuantityRequest request) {

        InventoryItemResponse inventoryItemResponse =  inventoryService.updateQuantity(
                marketId,
                productId,
                request.getQuantity()
        );

        ApiResponse<InventoryItemResponse> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Inventory item updated successfully",
                inventoryItemResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(apiResponse);
    }

    // ✅ DELETE
    @DeleteMapping("/{marketId}/inventory/{productId}")
    public void delete(@PathVariable Long marketId,
                       @PathVariable Long productId) {

        inventoryService.removeProduct(marketId, productId);
    }
}
