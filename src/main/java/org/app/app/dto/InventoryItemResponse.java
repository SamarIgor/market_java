package org.app.app.dto;

public class InventoryItemResponse {

    private Long productId;
    private String productName;
    private int quantity;

    public InventoryItemResponse(Long productId, String productName, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
}
