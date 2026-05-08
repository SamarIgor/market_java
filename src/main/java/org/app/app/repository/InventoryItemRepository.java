package org.app.app.repository;

import org.app.app.dto.InventoryItemResponse;
import org.app.app.model.Inventory;
import org.app.app.model.InventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByInventoryIdAndProductId(Long inventoryId, Long productId);

    Page<InventoryItem> findByInventoryId(Pageable pageable, Long id);
}
