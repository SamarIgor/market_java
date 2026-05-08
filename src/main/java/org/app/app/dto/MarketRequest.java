package org.app.app.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MarketRequest {

    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(
            regexp = "^[a-zA-ZÀ-ÿ\\s'-]+$",
            message = "Name contains invalid characters"
    )
    private String name;
    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(
            regexp = "^[a-zA-ZÀ-ÿ\\s'-]+$",
            message = "Organization contains invalid characters"
    )
    private String organization;
    @NotBlank
    @Size(max = 6, message = "ZIP code must contain 5 or 6 digits")
    private String zip;
    @NotBlank
    @Size(min = 5, max = 100)
    @Pattern(
            regexp = "^[a-zA-Z0-9À-ÿ\\s,./'-]+$",
            message = "Address contains invalid characters"
    )
    private String address;
    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(
            regexp = "^[a-zA-ZÀ-ÿ\\s'-]+$",
            message = "City contains invalid characters"
    )
    private String city;
    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(
            regexp = "^[a-zA-ZÀ-ÿ\\s'-]+$",
            message = "Country contains invalid characters"
    )
    private String country;

    public MarketRequest(String country, String city, String address, String zip, String organization, String name) {
        this.country = country;
        this.city = city;
        this.address = address;
        this.zip = zip;
        this.organization = organization;
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public String getOrganization() {
        return organization;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public void setName(String name) {
        this.name = name;
    }
}
