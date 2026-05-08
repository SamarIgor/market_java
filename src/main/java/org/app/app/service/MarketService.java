package org.app.app.service;

import jakarta.validation.Valid;
import org.app.app.dto.MarketRequest;
import org.app.app.dto.MarketResponse;
import org.app.app.exception.NotFoundException;
import org.app.app.mapper.MarketMapper;
import org.app.app.model.Inventory;
import org.app.app.model.Market;
import org.app.app.repository.MarketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarketService {

    private final MarketRepository marketRepository;
    private static final Logger log =
            LoggerFactory.getLogger(ProductService.class);

    public MarketService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    public List<MarketResponse> showAllMarkets() {
        log.info("Fetching all markets");

        List<MarketResponse> response = marketRepository.findAll()
                .stream()
                .map(MarketMapper::toResponse)
                .collect(Collectors.toList());

        log.info("Search found {} number of markets", response.size());
        return response;
    }

    public MarketResponse getMarketById(Long id) {
        log.info("Fetching market with id '{}'", id);

        Market market = marketRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Market with id '{}' not found", id);
                    return new NotFoundException("Market not found");
                });

        log.info("Market with id '{}' found", id);
        return MarketMapper.toResponse(market);
    }

    public MarketResponse addNewMarket(MarketRequest request) {
        log.info("Creating market with name {}", request.getName());

        Market m = MarketMapper.toEntity(request);
        Inventory inventory = new Inventory();

        m.setInventory(inventory);
        Market newMarket = marketRepository.save(m);

        log.info("Saved new market {}", newMarket);
        return MarketMapper.toResponse(newMarket);
    }

    public void deleteMarket(Long id) {
        log.info("Deleting market with id {}", id);

        if(!marketRepository.existsById(id)){
            log.warn("Market with id {} not found", id);
            throw new NotFoundException("Market with id '" + id + "' not found");
        }

        log.info("Deleted market with id {}", id);
        marketRepository.deleteById(id);
    }

    public MarketResponse updateMarket(Long id, @Valid MarketRequest request) {
        log.info("Updating market with id {}", id);

        Market m = marketRepository.findById(id).orElseThrow(() -> {
            log.warn("Market with id {} not found", id);
            return new RuntimeException("Market with id '"+id+"' not found");
        });

        m.setName(request.getName());
        m.setAddress(request.getAddress());
        m.setOrganization(request.getOrganization());
        m.setZip(request.getZip());
        m.setCity(request.getCity());
        m.setCountry(request.getCountry());

        Market updated = marketRepository.save(m);

        log.info("Saved market with id {}", id);
        return MarketMapper.toResponse(updated);
    }


}
