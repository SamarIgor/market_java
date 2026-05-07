package org.app.app.controller;

import org.app.app.dto.AddProductToInventoryRequest;
import org.app.app.dto.InventoryItemResponse;
import org.app.app.dto.MarketResponse;
import org.app.app.model.InventoryItem;
import org.app.app.response.ApiResponse;
import org.app.app.service.InventoryService;
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
    public ResponseEntity<ApiResponse<List<InventoryItemResponse>>> getInventory(@PathVariable Long marketId) {
        List<InventoryItemResponse> inventoryItemResponses = inventoryService.getInventory(marketId);
        ApiResponse<List<InventoryItemResponse>> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Inventory fetched successfully",
                inventoryItemResponses
        );
        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
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

    // ✅ PUT
    @PutMapping("/{marketId}/inventory/{productId}")
    public ResponseEntity<ApiResponse<InventoryItemResponse>> updateQuantity(
            @PathVariable Long marketId,
            @PathVariable Long productId,
            @RequestBody AddProductToInventoryRequest request) {

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
