package org.app.app.service;

import jakarta.transaction.Transactional;
import org.app.app.dto.InventoryItemResponse;
import org.app.app.exception.NotFoundException;
import org.app.app.mapper.InventoryMapper;
import org.app.app.model.Inventory;
import org.app.app.model.InventoryItem;
import org.app.app.model.Market;
import org.app.app.model.Product;
import org.app.app.repository.InventoryItemRepository;
import org.app.app.repository.MarketRepository;
import org.app.app.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final MarketRepository marketRepository;
    private final ProductRepository productRepository;
    private final InventoryItemRepository itemRepository;

    public InventoryService(MarketRepository marketRepository,
                            ProductRepository productRepository,
                            InventoryItemRepository itemRepository) {
        this.marketRepository = marketRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
    }

    // ✅ GET inventory
    public List<InventoryItemResponse> getInventory(Long marketId) {
        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new NotFoundException("Market not found"));

        Inventory inventory = market.getInventory();

//        return market.getInventory().getItems()
//                .stream()
//                .map(InventoryMapper::toResponse)
//                .toList();
        return itemRepository.findByInventoryId(inventory.getId())
                .stream()
                .map(InventoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ✅ ADD product
    @Transactional
    public InventoryItemResponse addProduct(Long marketId, Long productId, int quantity) {

        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new NotFoundException("Market not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Inventory inventory = market.getInventory();

        // check item already exits, if it does increment its quantity
//        for (InventoryItem item : inventory.getItems()) {
//            if (item.getProduct().getId().equals(productId)) {
//                item.setQuantity(item.getQuantity() + quantity);
//                return InventoryMapper.toResponse(item);
//            }
//        }
        Optional<InventoryItem> exists = itemRepository.findByInventoryIdAndProductId(inventory.getId(), productId);
        if(exists.isPresent()){
            InventoryItem item = exists.get();
            item.setQuantity(item.getQuantity() + quantity);
            return InventoryMapper.toResponse(item);
        }

        // create new
        InventoryItem newItem = new InventoryItem();
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        newItem.setInventory(inventory);

        itemRepository.save(newItem);
        return InventoryMapper.toResponse(newItem);
    }

    // ✅ UPDATE quantity
    @Transactional
    public InventoryItemResponse updateQuantity(Long marketId, Long productId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new NotFoundException("Market not found"));

        Inventory inventory = market.getInventory();

        InventoryItem item = itemRepository
                .findByInventoryIdAndProductId(inventory.getId(), productId)
                .orElseThrow(() -> new NotFoundException("Product not in inventory"));

        item.setQuantity(quantity);

        return InventoryMapper.toResponse(item);
    }

    // ✅ DELETE product
    @Transactional
    public void removeProduct(Long marketId, Long productId) {

        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new NotFoundException("Market not found"));

        Inventory inventory = market.getInventory();

        InventoryItem item = itemRepository
                .findByInventoryIdAndProductId(inventory.getId(), productId)
                .orElseThrow(() -> new NotFoundException("Product not in inventory"));

        itemRepository.delete(item);
    }
}
