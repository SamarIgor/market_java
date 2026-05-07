package org.app.app.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MarketRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String organization;
    @NotBlank
    @Size(max = 6, message = "Zip code isnt longer than 6")
    private String zip;
    @NotBlank
    private String address;
    @NotBlank
    private String city;
    @NotBlank
    private String country;

    public MarketRequest(Long id, String country, String city, String address, String zip, String organization, String name) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setName(String name) {
        this.name = name;
    }
}
