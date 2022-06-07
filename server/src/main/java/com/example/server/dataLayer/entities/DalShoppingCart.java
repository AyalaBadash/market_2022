package com.example.server.dataLayer.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table (name = "shopping_carts")
public class DalShoppingCart {
    @Id
    private int id;

//    @OneToMany(targetEntity = DalShoppingBasket.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "shopping_cart", referencedColumnName = "id")
//    @Column(name = "shop_name")
//    @MapKeyJoinColumn (name = "basket_id")

//    @OneToMany(targetEntity = DalShoppingBasket.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "shopping_cart", referencedColumnName = "id")
//    @Column(name = "shop_name")
//    @MapKeyJoinColumn(name = "basket_id")
//    private Map<String, DalShoppingBasket> baskets;
    @OneToMany(targetEntity =  DalShoppingBasket.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart", referencedColumnName = "id")
    private List<DalShoppingBasket> baskets;

    private double currentPrice;

    public DalShoppingCart(){}

    public DalShoppingCart(int id, List<DalShoppingBasket> baskets, double currentPrice) {
        this.id = id;
        this.baskets = baskets;
        this.currentPrice = currentPrice;
    }

    //    public DalShoppingCart(int id,Map<String, DalShoppingBasket> baskets, double currentPrice) {
//        this.id = id;
//        this.baskets = baskets;
//        this.currentPrice = currentPrice;
//    }
//
//    public void updateBaskets(Map<String, DalShoppingBasket> baskets){
//        this.baskets = baskets;
//    }

    public int getId() {
        return id;
    }

    public void setId(int ID) {
        this.id = ID;
    }

    public double getPrice() {
        return currentPrice;
    }

    public void setPrice(double price) {
        this.currentPrice = price;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

//    public Map<String, DalShoppingBasket> getBaskets() {
//        return baskets;
//    }
//
//    public void setBaskets(Map<String, DalShoppingBasket> baskets) {
//        this.baskets = baskets;
//    }


    public List<DalShoppingBasket> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<DalShoppingBasket> baskets) {
        this.baskets = baskets;
    }
}
