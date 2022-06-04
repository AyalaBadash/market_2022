package com.example.server.dataLayer.entities;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table
public class DalShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private double currentPrice;

//    @ElementCollection
//    @OneToMany
//    @JoinColumn (name = "shopping_cart", referencedColumnName = "id")
    @OneToMany(targetEntity = DalShoppingBasket.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart", referencedColumnName = "id")
    @MapKeyJoinColumn (name = "basket_id")
    private Map<String, DalShoppingBasket> baskets;


    public DalShoppingCart(){}

    public DalShoppingCart(int ID, double price) {
        this.Id = ID;
        this.currentPrice = price;
    }

    public int getId() {
        return Id;
    }

    public void setId(int ID) {
        this.Id = ID;
    }


    public double getPrice() {
        return currentPrice;
    }

    public void setPrice(double price) {
        this.currentPrice = price;
    }
}
