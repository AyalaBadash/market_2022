package com.example.server.dataLayer.entities;

import java.io.Serializable;

public class itemsForBasketID  implements Serializable {
    private int basketID;
    private int itemID;

    public itemsForBasketID(){}

    public itemsForBasketID(int basketID, int itemID) {
        this.basketID = basketID;
        this.itemID = itemID;
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
}
