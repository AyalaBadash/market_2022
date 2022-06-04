package com.example.server.dataLayer.entities;

import javax.persistence.*;

@Entity
@Table (name = "items_in_basket")
public class DalItemsForBasket {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn (name = "basket_id")
    private DalShoppingBasket shoppingBasket;
    @ManyToOne
    @JoinColumn (name = "item_id")
    private DalItem item;

    private Double amount;
    public DalItemsForBasket(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DalShoppingBasket getShoppingBasket() {
        return shoppingBasket;
    }

    public void setShoppingBasket(DalShoppingBasket shoppingBasket) {
        this.shoppingBasket = shoppingBasket;
    }

    public DalItem getItem() {
        return item;
    }

    public void setItem(DalItem item) {
        this.item = item;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
