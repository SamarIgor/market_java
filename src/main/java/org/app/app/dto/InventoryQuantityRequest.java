package org.app.app.dto;

import jakarta.validation.constraints.Min;

public class InventoryQuantityRequest {

    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;

    public InventoryQuantityRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}