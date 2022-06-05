package com.example.server.dataLayer.entities;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table (name = "shopping_baskets")
public class DalShoppingBasket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_id")
    private Integer id;
    @ElementCollection
    @CollectionTable(name="items_in_basket", joinColumns = {@JoinColumn(name = "basket_id")})
    @Column(name="amount")
    @MapKeyJoinColumn (name = "item_id")
    private Map<DalItem, Double> items;
    private double price;

    public DalShoppingBasket(double price, Map<DalItem, Double> items){
        this.price = price;
        this.items = items;
    }
    public DalShoppingBasket(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Map<DalItem, Double> getItems() {
        return items;
    }

    public void setItems(Map<DalItem, Double> items) {
        this.items = items;
    }
}
