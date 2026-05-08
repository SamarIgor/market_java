package org.app.app.dto;

import jakarta.validation.constraints.Min;

public class InventoryItemRequest {

    private Long productId;
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;

    private Long marketId;

    public Long getProductId() { return productId; }
    public int getQuantity() { return quantity; }

    public Long getMarketId() {
        return marketId;
    }
}
