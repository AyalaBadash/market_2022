package com.example.server.dataLayer.entities;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Entity
@Table (name = "shopping_baskets")
//@IdClass(ShoppingBasketID.class)
public class DalShoppingBasket {
//    @Id
//    private int cartID;
//    @Id
//    private String shopName;

    @Id
    @Column(name = "basket_id")
    private Integer id;
    private double price;
    @ElementCollection
    @CollectionTable(name="items_in_basket", joinColumns = {@JoinColumn(name = "basket_id")})
//    @MapKeyJoinColumn(name="item_id")
    @Column(name="amount")
    @MapKeyJoinColumn (name = "item_id")
    private Map<DalItem, Double> items;

//    @OneToMany (mappedBy = "shoppingBasket")
//    private Set<DalItemsForBasket> items;

    public DalShoppingBasket(int id, double price, Map<DalItem, Double> items){
        this.price = price;
//        this.items = items;
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

//    public Map<DalItem, Double> getItems() {
//        return items;
//    }
//
//    public void setItems(Map<DalItem, Double> items) {
//        this.items = items;
//    }


}
