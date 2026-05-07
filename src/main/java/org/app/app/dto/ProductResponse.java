package org.app.app.dto;

public class ProductResponse {
    private Long id;
    private String name;
    private double price;
    private String description;

    public ProductResponse(Long id, String name, double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    // getters

    public Long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
