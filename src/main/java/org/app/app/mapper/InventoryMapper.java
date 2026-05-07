package org.app.app.mapper;

import org.app.app.dto.InventoryItemResponse;
import org.app.app.model.InventoryItem;

public class InventoryMapper {

    public static InventoryItemResponse toResponse(InventoryItem item) {
        return new InventoryItemResponse(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity()
        );
    }
}