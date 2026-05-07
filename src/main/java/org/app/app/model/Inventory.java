package org.app.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "market_id")
    @JsonIgnore
    private Market market;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    private List<InventoryItem> items  = new ArrayList<>();

    public Inventory(){}

    public Inventory(Long id, List<InventoryItem> items, Market market) {
        this.id = id;
        this.items = items;
        this.market = market;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<InventoryItem> getItems() {
        return items;
    }

    public void setItems(List<InventoryItem> items) {
        this.items = items;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public void addItem(InventoryItem item) {
        items.add(item);
        item.setInventory(this);
    }

    public void removeItem(InventoryItem item) {
        items.remove(item);
        item.setInventory(null);
    }
}
