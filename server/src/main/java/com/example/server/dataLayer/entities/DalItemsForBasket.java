package com.example.server.dataLayer.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table
@IdClass(itemsForBasketID.class)
public class DalItemsForBasket {
    @Id
    private int basketID;
    @Id
    private int itemID;
    private int amount;
    public  DalItemsForBasket(){}

    public DalItemsForBasket(int basketID, int itemID, int amount) {
        this.basketID = basketID;
        this.itemID = itemID;
        this.amount = amount;
    }

    public int getBasketID() {
        return basketID;
    }

    public void setBasketID(int basketID) {
        this.basketID = basketID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
