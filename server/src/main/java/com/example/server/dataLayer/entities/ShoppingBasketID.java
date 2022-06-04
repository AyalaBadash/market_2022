package com.example.server.dataLayer.entities;

import java.io.Serializable;

public class ShoppingBasketID implements Serializable {
    private Integer cartID;
    private String shopName;

    public ShoppingBasketID(Integer cartID, String shopName) {
        this.cartID = cartID;
        this.shopName = shopName;
    }
    public ShoppingBasketID(){}

    public Integer getCartID() {
        return cartID;
    }

    public void setCartID(Integer cartID) {
        this.cartID = cartID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
