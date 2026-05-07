package org.app.app.dto;

public class AddProductToInventoryRequest {

    private Long productId;
    private int quantity;

    public Long getProductId() { return productId; }
    public int getQuantity() { return quantity; }
}