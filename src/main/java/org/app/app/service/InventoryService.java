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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final MarketRepository marketRepository;
    private final ProductRepository productRepository;
    private final InventoryItemRepository itemRepository;
    private static final Logger log =
            LoggerFactory.getLogger(ProductService.class);

    public InventoryService(MarketRepository marketRepository,
                            ProductRepository productRepository,
                            InventoryItemRepository itemRepository) {
        this.marketRepository = marketRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
    }

    // ✅ GET inventory
    public List<InventoryItemResponse> getInventory(Long marketId) {
        log.info("Fetching all items from inventory");

        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> {
                    log.warn("GET Inventory: Market with id " + marketId + " not found");
                    return new NotFoundException("Market not found");
                });

        Inventory inventory = market.getInventory();
        List<InventoryItemResponse> items = itemRepository.findByInventoryId(inventory.getId())
                .stream()
                .map(InventoryMapper::toResponse)
                .collect(Collectors.toList());

        log.info("Search found {} number of items in inventory", items.size());

        return items;
    }

    // ✅ ADD product
    @Transactional
    public InventoryItemResponse addProduct(Long marketId, Long productId, int quantity) {
        log.info("Adding new item with product id '{}' into market id '{}'", productId, marketId);

        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> {
                    log.warn("POST Inventory Item: Market with id '{}' not found", marketId);
                    return new NotFoundException("Market not found");
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("POST Inventory Item: Product with id '{}' not found", productId);
                    return new NotFoundException("Product not found");
                });

        Inventory inventory = market.getInventory();

        Optional<InventoryItem> exists = itemRepository.findByInventoryIdAndProductId(inventory.getId(), productId);
        if(exists.isPresent()){
            InventoryItem item = exists.get();
            item.setQuantity(item.getQuantity() + quantity);
            log.info("Upadated item's '{}' quantity to {}", item.getProduct().getName(), item.getQuantity());
            return InventoryMapper.toResponse(item);
        }

        // create new
        InventoryItem newItem = new InventoryItem();
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        newItem.setInventory(inventory);

        itemRepository.save(newItem);

        log.info("Saved new item '{}'", newItem.getProduct().getName());
        return InventoryMapper.toResponse(newItem);
    }

    // ✅ UPDATE quantity
    @Transactional
    public InventoryItemResponse updateQuantity(Long marketId, Long productId, int quantity) {
        log.info("Updating item with product id '{}' in market id '{}'", productId, marketId);

        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> {
                    log.warn("PUT Inventory Item: Market with id '{}' not found", marketId);
                    return new NotFoundException("Market not found");
                });

        Inventory inventory = market.getInventory();

        InventoryItem item = itemRepository
                .findByInventoryIdAndProductId(inventory.getId(), productId)
                .orElseThrow(() -> {
                    log.warn("PUT Inventory Item: Product with id '{}' not found", productId);
                    return new NotFoundException("Product not found");
                });

        item.setQuantity(quantity);

        log.info("Updated quantity of item '{}' to {}", item.getProduct().getName(), item.getQuantity());
        return InventoryMapper.toResponse(item);
    }

    // ✅ DELETE product
    @Transactional
    public void removeProduct(Long marketId, Long productId) {

        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> {
                    log.warn("DELETE Inventory Item: Market with id '{}' not found", marketId);
                    return new NotFoundException("Market not found");
                });

        Inventory inventory = market.getInventory();

        InventoryItem item = itemRepository
                .findByInventoryIdAndProductId(inventory.getId(), productId)
                .orElseThrow(() -> {
                    log.warn("DELETE Inventory Item: Product with id '{}' not found", productId);
                    return new NotFoundException("Product not found");
                });

        itemRepository.delete(item);
        log.info("Deleted item with product '{}' from market '{}'", productId, marketId);
    }

    public InventoryItemResponse getInventoryItem(Long marketId, Long productId) {
        log.info("Searching for item with product id '{}' into market id '{}'", productId, marketId);

        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> {
                    log.warn("POST Inventory Item: Market with id '{}' not found", marketId);
                    return new NotFoundException("Market not found");
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("POST Inventory Item: Product with id '{}' not found", productId);
                    return new NotFoundException("Product not found");
                });

        Inventory inventory = market.getInventory();
        InventoryItem item = itemRepository.findByInventoryIdAndProductId(inventory.getId(), productId)
                .orElseThrow(() -> {
                    log.warn("GET/id Inventory item: Product with id '{}' not found in inventory", productId);
                    return new NotFoundException("Item not found");
                });

        log.info("Successfully found product '{}' in market '{}'", productId, marketId);
        return InventoryMapper.toResponse(item);
    }
}
