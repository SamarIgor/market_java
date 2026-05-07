package org.app.app.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MarketResponse {

    private Long id;
    private String name;
    private String organization;
    private String zip;
    private String address;
    private String city;
    private String country;

    public MarketResponse(Long id, String country, String city, String address, String zip, String organization, String name) {
        this.id = id;
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

    public Long getId() {
        return id;
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

}
