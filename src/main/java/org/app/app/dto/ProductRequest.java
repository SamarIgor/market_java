package org.app.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(
            regexp = "^[a-zA-Z0-9À-ÿ\\s'-]+$",
            message = "Name contains invalid characters"
    )
    private String name;

    @Positive(message = "Price must be greater than 0")
    private double price;

    @NotBlank
    @Size(min = 2, max = 500)
    private String description;

    // getters/setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
