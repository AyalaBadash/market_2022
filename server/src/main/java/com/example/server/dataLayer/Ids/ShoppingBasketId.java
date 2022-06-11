package com.example.server.dataLayer.Ids;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
@Embeddable
public class ShoppingBasketId implements Serializable {
    public String shop_name;
    public Integer Shopping_cart;

    public ShoppingBasketId(String shop_name, Integer shopping_cart) {
        this.shop_name = shop_name;
        Shopping_cart = shopping_cart;
    }

    public ShoppingBasketId(){}

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public Integer getShopping_cart() {
        return Shopping_cart;
    }

    public void setShopping_cart(Integer shopping_cart) {
        Shopping_cart = shopping_cart;
    }
}
