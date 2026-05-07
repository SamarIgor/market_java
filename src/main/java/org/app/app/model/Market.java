package org.app.app.model;

import jakarta.persistence.*;

@Entity
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String organization;
    private String zip;
    private String address;
    private String city;
    private String country;

    @OneToOne(mappedBy = "market", cascade = CascadeType.ALL)
    private Inventory inventory;

    public Market(){}

    public Market(Long id, String country, String city, String address, String zip, String organization, String name) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.address = address;
        this.zip = zip;
        this.organization = organization;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        inventory.setMarket(this);
    }
}
