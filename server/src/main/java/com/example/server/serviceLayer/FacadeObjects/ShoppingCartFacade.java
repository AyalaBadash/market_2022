package com.example.server.serviceLayer.FacadeObjects;

import com.example.server.businessLayer.Shop;
import com.example.server.businessLayer.ShoppingBasket;
import com.example.server.businessLayer.ShoppingCart;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartFacade implements FacadeObject<ShoppingCart> {
    Map<ShopFacade, ShoppingBasketFacade> cart; // <Shop ,basket for the shop>

    public ShoppingCartFacade(Map<ShopFacade, ShoppingBasketFacade> cart) {
        this.cart = cart;
    }

    public ShoppingCartFacade(ShoppingCart cart) {
        this.cart = new HashMap<>();
        for (Map.Entry<Shop, ShoppingBasket> fromCart: cart.getCart().entrySet()){
            ShopFacade toShop = new ShopFacade(fromCart.getKey());
            ShoppingBasketFacade toBasket = new ShoppingBasketFacade(fromCart.getValue());
            this.cart.put(toShop,toBasket);
        }

    }

    public Map<ShopFacade, ShoppingBasketFacade> getCart() {
        return cart;
    }

    public void setCart(Map<ShopFacade, ShoppingBasketFacade> cart) {
        this.cart = cart;
    }

    @Override
    public ShoppingCart toBusinessObject() {
        return null;
    }
}