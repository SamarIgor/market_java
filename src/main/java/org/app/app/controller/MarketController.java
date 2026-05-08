package org.app.app.controller;

import jakarta.validation.Valid;
import org.app.app.dto.MarketRequest;
import org.app.app.dto.MarketResponse;
import org.app.app.response.ApiResponse;
import org.app.app.service.MarketService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/market")
public class MarketController {

    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<MarketResponse>>> getAllMarkets(
            @PageableDefault(size = 10, sort = "name")
            @ParameterObject Pageable pageable){
        Page<MarketResponse> response = marketService.showAllMarkets(pageable);;

        ApiResponse<Page<MarketResponse>> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Markets fetched successfully",
                response
        );
        return ResponseEntity.status(HttpStatus.OK.value()).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MarketResponse>> getMarketById(
            @PathVariable Long id) {

        MarketResponse response = marketService.getMarketById(id);

        ApiResponse<MarketResponse> apiResponse =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Market fetched successfully",
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MarketResponse>> postMarket(@RequestBody @Valid MarketRequest request){
        MarketResponse response = marketService.addNewMarket(request);

        ApiResponse<MarketResponse> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Market inserted successfully",
                response
        );
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(apiResponse);
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<MarketResponse>>> insertMoreMarkets(
            @RequestBody @Valid List<MarketRequest> request){
        List<MarketResponse> response = marketService.addMoreNewMarkets(request);

        ApiResponse<List<MarketResponse>> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Market inserted successfully",
                response
        );
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MarketResponse>> putMarket(@PathVariable Long id, @RequestBody @Valid MarketRequest request){
        MarketResponse response = marketService.updateMarket(id, request);

        ApiResponse<MarketResponse> apiResponse = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Market with id '" + id + "' updated successfully",
                response
        );
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMarket(@PathVariable Long id){
        marketService.deleteMarket(id);

        ApiResponse<Void> apiResponse = new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "Market deleted successfully",
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(apiResponse);
    }
}
