package com.example.server.dataLayer.entities;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table (name = "shopping_carts")
public class DalShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

//    @OneToMany(targetEntity = DalShoppingBasket.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "shopping_cart", referencedColumnName = "id")
//    @Column(name = "shop_name")
//    @MapKeyJoinColumn (name = "basket_id")

    @OneToMany(targetEntity = DalShoppingBasket.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart", referencedColumnName = "id")
    @MapKeyJoinColumn(name = "shop_name")
    private Map<DalShop, DalShoppingBasket> baskets;

    private double currentPrice;


    public DalShoppingCart(){}

    public DalShoppingCart(Map<DalShop, DalShoppingBasket> baskets, double currentPrice) {
        this.baskets = baskets;
        this.currentPrice = currentPrice;
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

//    public Map<String, DalShoppingBasket> getBaskets() {
//        return baskets;
//    }
//
//    public void setBaskets(Map<String, DalShoppingBasket> baskets) {
//        this.baskets = baskets;
//    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
